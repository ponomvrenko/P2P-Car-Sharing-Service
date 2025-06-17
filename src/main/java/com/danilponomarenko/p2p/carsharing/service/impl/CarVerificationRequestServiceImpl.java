package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.verification.CarVerificationRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.verification.VerificationDocDto;
import com.danilponomarenko.p2p.carsharing.exception.EntityNotFoundException;
import com.danilponomarenko.p2p.carsharing.mapper.CarMapper;
import com.danilponomarenko.p2p.carsharing.mapper.UserMapper;
import com.danilponomarenko.p2p.carsharing.model.Car;
import com.danilponomarenko.p2p.carsharing.model.User;
import com.danilponomarenko.p2p.carsharing.repository.CarRepository;
import com.danilponomarenko.p2p.carsharing.repository.UserRepository;
import com.danilponomarenko.p2p.carsharing.service.CarVerificationRequestService;
import com.danilponomarenko.p2p.carsharing.service.VerificationDocService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarVerificationRequestServiceImpl implements CarVerificationRequestService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final VerificationDocService verificationDocService;

    @Override
    public List<CarVerificationRequestDto> getPendingVerificationRequests() {
        List<Car> cars = carRepository.findByStatus(Car.CarStatus.PENDING_VERIFICATION);
        List<CarVerificationRequestDto> result = new ArrayList<>();
        for (Car car : cars) {
            User owner = userRepository.findById(car.getOwner().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Owner not found for car: " + car.getId())
                    );
            List<VerificationDocDto> docs = verificationDocService.getDocsByUser(owner.getId());
            CarVerificationRequestDto dto = new CarVerificationRequestDto();
            dto.setRequestId(car.getId());
            dto.setCar(carMapper.toDto(car));
            dto.setOwner(userMapper.toDto(owner));
            dto.setDocuments(docs);
            dto.setStatus(car.getStatus().name());
            dto.setCreatedAt(car.getCarPhotos().getLast().getUploadedAt());
            result.add(dto);
        }
        return result;
    }

    @Override
    public void approve(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        car.setStatus(Car.CarStatus.AVAILABLE);

        User user = userRepository.findByEmail(car.getOwner().getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Owner not found for car: " + carId
                ));
        user.setVerified(true);

        carRepository.save(car);
    }

    @Override
    public void reject(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        car.setStatus(Car.CarStatus.UNAVAILABLE);
        carRepository.save(car);
    }
}
