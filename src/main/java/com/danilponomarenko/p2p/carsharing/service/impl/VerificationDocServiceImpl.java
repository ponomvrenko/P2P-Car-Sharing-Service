package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.verification.VerificationDocDto;
import com.danilponomarenko.p2p.carsharing.mapper.VerificationDocMapper;
import com.danilponomarenko.p2p.carsharing.model.User;
import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import com.danilponomarenko.p2p.carsharing.repository.UserRepository;
import com.danilponomarenko.p2p.carsharing.repository.VerificationDocRepository;
import com.danilponomarenko.p2p.carsharing.service.VerificationDocService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class VerificationDocServiceImpl implements VerificationDocService {
    private final VerificationDocRepository verificationDocRepository;
    private final UserRepository userRepository;
    private final GcsService gcsService;
    private final VerificationDocMapper verificationDocMapper;

    @Override
    public VerificationDocDto uploadDoc(
            Long userId,
            VerificationDoc.VerificationDocType docType,
            MultipartFile file
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        String url;
        try {
            url = gcsService.uploadFile(file, file.getOriginalFilename());
        } catch (Exception e) {
            // Можно залогировать ошибку (логгер или System.err)
            throw new RuntimeException(
                    "Не удалось загрузить файл в облако: " + file.getOriginalFilename(), e
            );
        }

        VerificationDoc doc = VerificationDoc.builder()
                .user(user)
                .docType(docType)
                .docUrl(url)
                .uploadedAt(LocalDateTime.now())
                .build();

        verificationDocRepository.save(doc);

        return verificationDocMapper.toDto(doc);
    }

    @Override
    public List<VerificationDocDto> getDocsByUser(Long userId) {
        List<VerificationDoc> docs = verificationDocRepository.findByUserId(userId);
        List<VerificationDocDto> result = new ArrayList<>();
        for (VerificationDoc doc : docs) {
            result.add(verificationDocMapper.toDto(doc));
        }
        return result;
    }

    @Override
    public void deleteDoc(Long docId) {
        VerificationDoc doc = verificationDocRepository.findById(docId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Verification doc not found: " + docId
                ));
        // Удаляем файл из облака
        gcsService.deleteFileByUrl(doc.getDocUrl());
        // Удаляем запись из БД
        verificationDocRepository.delete(doc);
    }
}
