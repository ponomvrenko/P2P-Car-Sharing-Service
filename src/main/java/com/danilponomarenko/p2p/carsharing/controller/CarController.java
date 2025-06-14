package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<CarResponseDto> addCar(
            @RequestBody CarCreateRequestDto carCreateRequestDto
    ) {
        CarResponseDto carResponseDto = carService.addCar(carCreateRequestDto);
        return new ResponseEntity<>(carResponseDto, HttpStatus.CREATED);
    }
}
