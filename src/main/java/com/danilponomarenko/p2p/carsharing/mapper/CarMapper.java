package com.danilponomarenko.p2p.carsharing.mapper;

import com.danilponomarenko.p2p.carsharing.config.MapperConfig;
import com.danilponomarenko.p2p.carsharing.dto.car.CarRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.model.Car;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarResponseDto toDto(Car car);

    Car toModel(CarRequestDto requestDto);
}
