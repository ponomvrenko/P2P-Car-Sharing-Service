package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.car.CarRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.service.CarService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CarServiceImpl implements CarService {

    @Override
    public List<CarResponseDto> getAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public CarResponseDto getById(Long id) {
        return null;
    }

    @Override
    public CarResponseDto add(CarRequestDto requestDto) {
        return null;
    }

    @Override
    public CarResponseDto update(Long id, CarRequestDto requestDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
