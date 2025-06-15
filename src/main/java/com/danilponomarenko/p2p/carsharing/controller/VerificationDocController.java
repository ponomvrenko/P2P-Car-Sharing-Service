package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.verification.VerificationDocDto;
import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import com.danilponomarenko.p2p.carsharing.service.VerificationDocService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/verification-docs")
public class VerificationDocController {
    private final VerificationDocService verificationDocService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<VerificationDocDto> uploadDoc(
            @RequestParam("userId") Long userId,
            @RequestParam("docType") VerificationDoc.VerificationDocType docType,
            @RequestParam("file") MultipartFile file) {
        try {
            VerificationDocDto result = verificationDocService.uploadDoc(userId, docType, file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VerificationDocDto>> getDocsByUser(@PathVariable Long userId) {
        try {
            List<VerificationDocDto> docs = verificationDocService.getDocsByUser(userId);
            return ResponseEntity.ok(docs);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{docId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDoc(@PathVariable Long docId) {
        try {
            verificationDocService.deleteDoc(docId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

