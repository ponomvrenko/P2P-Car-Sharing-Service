package com.danilponomarenko.p2p.carsharing.mapper;

import com.danilponomarenko.p2p.carsharing.config.MapperConfig;
import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.model.Car;
import com.danilponomarenko.p2p.carsharing.model.CarPhoto;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    @Mapping(source = "carPhotos",
            target = "primaryPhotoUrl",
            qualifiedByName = "extractPrimaryPhotoUrl"
    )
    @Mapping(source = "carPhotos",
            target = "photoUrls",
            qualifiedByName = "extractPhotoUrls"
    )
    CarResponseDto toDto(Car car);

    Car toModel(CarCreateRequestDto requestDto);

    @Named("extractPrimaryPhotoUrl")
    default String extractPrimaryPhotoUrl(java.util.List<CarPhoto> carPhotos) {
        if (carPhotos == null || carPhotos.isEmpty()) {
            return null;
        }
        return carPhotos.stream()
                .filter(CarPhoto::isPrimary)
                .findFirst()
                .map(CarPhoto::getUrl)
                .orElse(carPhotos.getFirst().getUrl());
    }

    @Named("extractPhotoUrls")
    default List<String> extractPhotoUrls(List<CarPhoto> carPhotos) {
        if (carPhotos == null) {
            return Collections.emptyList();
        }
        return carPhotos.stream()
                .map(CarPhoto::getUrl)
                .toList();
    }
}
