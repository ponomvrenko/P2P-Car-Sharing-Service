/*
package com.danilponomarenko.p2p.carsharing.dto.car;

import com.danilponomarenko.p2p.carsharing.model.Car;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CarRequestDto {

    @NotBlank(message = "Brand is required!")
    @Size(max = 255, message = "Brand must not exceed 255 characters!")
    private String brand;

    @NotBlank(message = "Model is required!")
    @Size(max = 255, message = "model must not exceed 255 characters!")
    private String model;

    @NotNull(message = "Car type is required!")
    private Car.CarType type;

    @Min(value = 1900, message = "Year must be realistic!")
    private int year;

    @NotBlank(message = "Color is required!")
    private String color;

    @NotNull(message = "Daily fee is required!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily fee must be positive!")
    private BigDecimal dailyFee;

    @Size(max = 2048, message = "Description is too long!")
    private String description;

    @DecimalMin(value = "0.5", message = "Engine volume must be at least 0.5L!")
    private BigDecimal engineVolume;

    @Min(value = 0, message = "Mileage must be non-negative!")
    private int mileage;

    @NotNull(message = "Transmission type is required!")
    private Car.Transmission transmission;

    @NotNull(message = "Fuel type is required!")
    private Car.FuelType fuelType;

    @Min(value = 1, message = "Must have at least 1 seat!")
    private Integer seats;

    @NotNull(message = "LocationId is required!")
    private Long locationId;
}
*/
