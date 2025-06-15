package com.danilponomarenko.p2p.carsharing.mapper;

import com.danilponomarenko.p2p.carsharing.config.MapperConfig;
import com.danilponomarenko.p2p.carsharing.dto.location.LocationDto;
import com.danilponomarenko.p2p.carsharing.model.Location;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface LocationMapper {
    Location toModel(LocationDto locationDto);

    LocationDto toDto(Location location);
}
