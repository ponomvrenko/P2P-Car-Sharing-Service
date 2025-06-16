package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.verification.VerificationDocDto;
import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface VerificationDocService {
    VerificationDocDto uploadDoc(
            String userEmail,
            VerificationDoc.VerificationDocType docType,
            MultipartFile file
    );

    List<VerificationDocDto> getDocsByUser(Long userId);

    void deleteDoc(Long docId);
}
