package com.danilponomarenko.p2p.carsharing.dto.car.photo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarPhotoDto {
    private Long id;
    private String url;
    private LocalDateTime uploadedAt;
    private boolean isPrimary;
    private Long carId;
}
