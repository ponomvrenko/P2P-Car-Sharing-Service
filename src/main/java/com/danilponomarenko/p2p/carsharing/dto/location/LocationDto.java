package com.danilponomarenko.p2p.carsharing.dto.location;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LocationDto {
    private String name;
    private String address;
    private String city;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
}

