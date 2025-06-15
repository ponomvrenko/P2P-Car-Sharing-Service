package com.danilponomarenko.p2p.carsharing.mapper;

import com.danilponomarenko.p2p.carsharing.config.MapperConfig;
import com.danilponomarenko.p2p.carsharing.dto.verification.VerificationDocDto;
import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface VerificationDocMapper {
    @Mapping(target = "userId", source = "user.id")
    VerificationDocDto toDto(VerificationDoc verificationDoc);
}
