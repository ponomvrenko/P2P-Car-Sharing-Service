package com.danilponomarenko.p2p.carsharing.dto.verification;

import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.user.UserResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CarVerificationRequestDto {
    private Long requestId;
    private CarResponseDto car;
    private UserResponseDto owner;
    private List<VerificationDocDto> documents;
    private String status;
    private LocalDateTime createdAt;
}

