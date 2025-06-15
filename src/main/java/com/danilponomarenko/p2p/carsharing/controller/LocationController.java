package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.location.LocationDto;
import com.danilponomarenko.p2p.carsharing.model.Location;
import com.danilponomarenko.p2p.carsharing.repository.LocationRepository;
import com.danilponomarenko.p2p.carsharing.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Long> createLocation(@RequestBody LocationDto locationDto) {
        Location location = locationMapper.toModel(locationDto);
        Location saved = locationRepository.save(location);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            LocationDto dto = locationMapper.toDto(location.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Опционально: получить все локации
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        List<LocationDto> dtos = locations.stream()
                .map(locationMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}

