package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.verification.CarVerificationRequestDto;
import java.util.List;

public interface CarVerificationRequestService {
    List<CarVerificationRequestDto> getPendingVerificationRequests();

    void approve(Long carId);

    void reject(Long carId);
}
