package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.car.CarCreateRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.car.CarUpdateRequestDto;
import com.danilponomarenko.p2p.carsharing.exception.EntityNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public List<CarResponseDto> getAllAvailableCars() {
        return carRepository.findByStatus(Car.CarStatus.AVAILABLE)
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getCarsByOwner(String email) {
        List<Car> cars = carRepository.findByOwnerEmail(email);
        return cars.stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto updateCar(Long id, CarUpdateRequestDto requestDto, String ownerEmail) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car with id " + id + " not found"));

        // Проверка владельца
        if (!car.getOwner().getEmail().equals(ownerEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You do not have permission to update this car"
            );
        }

        // Обновляем только разрешённые поля
        if (requestDto.getColor() != null) {
            car.setColor(requestDto.getColor());
        }

        if (requestDto.getMileage() != null) {
            car.setMileage(requestDto.getMileage());
        }

        if (requestDto.getDescription() != null) {
            car.setDescription(requestDto.getDescription());
        }

        if (requestDto.getDailyFee() != null) {
            car.setDailyFee(requestDto.getDailyFee());
        }

        if (requestDto.getSeats() != null) {
            car.setSeats(requestDto.getSeats());
        }

        if (requestDto.getStatus() != null) {
            if (requestDto.getStatus() == Car.CarStatus.AVAILABLE
                    || requestDto.getStatus() == Car.CarStatus.UNAVAILABLE
                    || requestDto.getStatus() == Car.CarStatus.PENDING_VERIFICATION
                    || requestDto.getStatus() == Car.CarStatus.RENTED
                    || requestDto.getStatus() == Car.CarStatus.UNDER_MAINTENANCE
            ) {
                car.setStatus(requestDto.getStatus());
            } else {
                throw new IllegalArgumentException("Invalid car status: " + requestDto.getStatus());
            }
        }

        if (requestDto.getLocation() != null) {
            Location location = new Location();
            location.setName(requestDto.getLocation().getName());
            location.setAddress(requestDto.getLocation().getAddress());
            location.setCity(requestDto.getLocation().getCity());
            location.setPostalCode(requestDto.getLocation().getPostalCode());
            location.setLatitude(requestDto.getLocation().getLatitude());
            location.setLongitude(requestDto.getLocation().getLongitude());
            car.setLocation(location);
        }

        if (requestDto.getLocation() != null) {
            Location location = new Location();
            location.setName(requestDto.getLocation().getName());
            location.setCity(requestDto.getLocation().getCity());
            location.setAddress(requestDto.getLocation().getAddress());
            location.setPostalCode(requestDto.getLocation().getPostalCode());
            location.setLatitude(requestDto.getLocation().getLatitude());
            location.setLongitude(requestDto.getLocation().getLongitude());
            locationRepository.save(location);
            car.setLocation(location);
        }

        if (requestDto.getPhotoUrls() != null) {
            List<CarPhoto> photos = requestDto.getPhotoUrls().stream()
                    .map(url -> {
                        CarPhoto photo = new CarPhoto();
                        photo.setUrl(url);
                        photo.setCar(car);
                        photo.setUploadedAt(LocalDateTime.now());
                        return photo;
                    })
                    .collect(Collectors.toList());
            car.setCarPhotos(photos);
        }

        // Сохраняем изменения
        Car updated = carRepository.save(car);
        return carMapper.toDto(updated);
    }

    @Override
    public List<CarResponseDto> getPendingCars() {
        return carRepository.findByStatus(Car.CarStatus.UNAVAILABLE).stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto approveCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car with id " + id + " not found"));

        if (car.getStatus() != Car.CarStatus.UNAVAILABLE) {
            throw new IllegalStateException("Only UNAVAILABLE cars can be approved");
        }

        car.setStatus(Car.CarStatus.AVAILABLE);
        Car saved = carRepository.save(car);

        return carMapper.toDto(saved);
    }

    @Override
    public CarResponseDto getCarById(Long id) {
        return carRepository.findById(id)
                .map(carMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Car with id " + id + " not found"));
    }

    @Override
    public void deleteCar(Long id, String ownerEmail) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car with id " + id + " not found"));

        // Проверка владельца
        if (!car.getOwner().getEmail().equals(ownerEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You do not have permission to delete this car"
            );
        }

        // Удаляем фотографии
        List<CarPhoto> photos = carPhotoRepository.findByCarId(car.getId());
        carPhotoRepository.deleteAll(photos);

        // Удаляем верификационные документы
        List<VerificationDoc> docs = verificationDocRepository.findByUserId(car.getOwner().getId());
        verificationDocRepository.deleteAll(docs);

        // Удаляем локацию
        Location location = car.getLocation();
        if (location != null) {
            locationRepository.delete(location);
        }

        // Удаляем сам автомобиль
        carRepository.delete(car);
    }
}
