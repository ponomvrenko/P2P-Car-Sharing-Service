package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.mapper.CarMapper;
import com.danilponomarenko.p2p.carsharing.mapper.LocationMapper;
import com.danilponomarenko.p2p.carsharing.model.Car;
import com.danilponomarenko.p2p.carsharing.model.CarPhoto;
import com.danilponomarenko.p2p.carsharing.model.Location;
import com.danilponomarenko.p2p.carsharing.model.User;
import com.danilponomarenko.p2p.carsharing.model.VerificationDoc;
import com.danilponomarenko.p2p.carsharing.repository.CarPhotoRepository;
import com.danilponomarenko.p2p.carsharing.repository.CarRepository;
import com.danilponomarenko.p2p.carsharing.repository.LocationRepository;
import com.danilponomarenko.p2p.carsharing.repository.UserRepository;
import com.danilponomarenko.p2p.carsharing.repository.VerificationDocRepository;
import com.danilponomarenko.p2p.carsharing.service.CarService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarPhotoRepository carPhotoRepository;
    private final LocationRepository locationRepository;
    private final VerificationDocRepository verificationDocRepository;
    private final CarMapper carMapper;
    private final LocationMapper locationMapper;

    @Transactional
    @Override
    public CarResponseDto addCar(CarCreateRequestDto dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 1. Преобразуем DTO в Car и задаем владельца
        Car car = carMapper.toModel(dto);
        Location location = locationMapper.toModel(dto.getLocation());
        locationRepository.save(location);
        car.setLocation(location);
        car.setOwner(currentUser);
        car.setStatus(Car.CarStatus.UNAVAILABLE);
        Car savedCar = carRepository.save(car);

        // 2. Привязываем CarPhoto
        if (dto.getPhotoUrls() != null) {
            List<CarPhoto> photos = dto.getPhotoUrls().stream()
                    .map(url -> CarPhoto.builder()
                            .url(url)
                            .car(savedCar)
                            .uploadedAt(LocalDateTime.now())
                            .isPrimary(false)
                            .build())
                    .collect(Collectors.toList());
            carPhotoRepository.saveAll(photos);
            savedCar.setCarPhotos(photos);
        }

        // 3. Привязываем верифікаційні документи (необязательно)
        if (dto.getVerificationDocs() != null) {
            List<VerificationDoc> docs = dto.getVerificationDocs().stream()
                    .map(doc -> VerificationDoc.builder()
                            .docType(doc.getDocType())
                            .docUrl(doc.getDocUrl())
                            .uploadedAt(LocalDateTime.now())
                            .user(currentUser)
                            .build())
                    .collect(Collectors.toList());
            verificationDocRepository.saveAll(docs);
        }

        return carMapper.toDto(savedCar);
    }
}
