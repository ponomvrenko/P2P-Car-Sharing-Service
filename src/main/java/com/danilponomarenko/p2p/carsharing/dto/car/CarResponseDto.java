package com.danilponomarenko.p2p.carsharing.dto.car;

import com.danilponomarenko.p2p.carsharing.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarResponseDto {

    private Long id;

    private String brand;

    private String model;

    private Car.CarType type;

    private int year;

    private String color;

    private BigDecimal dailyFee;

    private String description;

    private BigDecimal engineVolume;

    private int mileage;

    private Car.Transmission transmission;

    private Car.FuelType fuelType;

    private Integer seats;

    private Car.CarStatus status;

    // TODO: add LocationDto or locationId if needed
    private String locationName; // або об'єкт LocationDto за потреби

}
