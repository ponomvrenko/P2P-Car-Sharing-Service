package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarUpdateRequestDto;
import com.danilponomarenko.p2p.carsharing.service.CarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public List<CarResponseDto> getAllAvailableCars() {
        return carService.getAllAvailableCars();
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/my")
    public List<CarResponseDto> getMyCars(Authentication authentication) {
        return carService.getCarsByOwner(authentication.getName());
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public CarResponseDto updateCar(
            @PathVariable Long id,
            @RequestBody CarUpdateRequestDto requestDto,
            Authentication authentication) {
        return carService.updateCar(id, requestDto, authentication.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public List<CarResponseDto> getPendingCars() {
        return carService.getPendingCars();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public CarResponseDto approveCar(@PathVariable Long id) {
        return carService.approveCar(id);
    }
}
