package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarUpdateRequestDto;
import java.util.List;

public interface CarService {
    CarResponseDto addCar(CarCreateRequestDto carCreateRequestDto);

    List<CarResponseDto> getAllAvailableCars();

    List<CarResponseDto> getCarsByOwner(String email);

    CarResponseDto updateCar(Long id, CarUpdateRequestDto requestDto, String ownerEmail);

    List<CarResponseDto> getPendingCars();

    CarResponseDto approveCar(Long id);

    CarResponseDto getCarById(Long id);
}
