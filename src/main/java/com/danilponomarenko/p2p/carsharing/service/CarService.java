package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;

public interface CarService {
    CarResponseDto addCar(CarCreateRequestDto carCreateRequestDto);
}
