package com.danilponomarenko.p2p.carsharing.dto.car;

import com.danilponomarenko.p2p.carsharing.dto.location.LocationDto;
import com.danilponomarenko.p2p.carsharing.dto.verification.VerificationDocDto;
import com.danilponomarenko.p2p.carsharing.model.Car;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CarUpdateRequestDto {
    private String color;
    private Integer mileage;
    private String description;
    private BigDecimal dailyFee;
    private Integer seats;
    private Car.CarStatus status;
    private LocationDto location;
    private List<String> photoUrls;
    private List<VerificationDocDto> verificationDocs;
}
