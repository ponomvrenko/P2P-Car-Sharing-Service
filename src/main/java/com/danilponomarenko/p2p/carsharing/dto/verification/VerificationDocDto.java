package com.danilponomarenko.p2p.carsharing.dto.verification;

import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VerificationDocDto {
    private VerificationDoc.VerificationDocType docType;
    private LocalDateTime uploadedAt;
    private String docUrl;
    private Long userId;
}

