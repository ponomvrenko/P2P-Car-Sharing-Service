package com.danilponomarenko.p2p.carsharing.dto.verification;

import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import lombok.Data;

@Data
public class VerificationDocDto {
    private VerificationDoc.VerificationDocType docType;
    private String docUrl;
}

