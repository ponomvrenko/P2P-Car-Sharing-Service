package com.danilponomarenko.p2p.carsharing.mapper;

import com.danilponomarenko.p2p.carsharing.config.MapperConfig;
import com.danilponomarenko.p2p.carsharing.dto.car.photo.CarPhotoDto;
import com.danilponomarenko.p2p.carsharing.model.CarPhoto;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CarPhotoMapper {
    CarPhotoDto toDto(CarPhoto carPhoto);
}
