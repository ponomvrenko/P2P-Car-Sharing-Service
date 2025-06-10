package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.car.CarRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CarService {
    List<CarResponseDto> getAll(Pageable pageable);

    CarResponseDto getById(Long id);

    CarResponseDto add(CarRequestDto requestDto);

    CarResponseDto update(Long id, CarRequestDto requestDto);

    void delete(Long id);
}
