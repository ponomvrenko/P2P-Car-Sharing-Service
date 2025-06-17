package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.user.UserResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.verification.CarVerificationRequestDto;
import com.danilponomarenko.p2p.carsharing.exception.EntityNotFoundException;
import com.danilponomarenko.p2p.carsharing.mapper.UserMapper;
import com.danilponomarenko.p2p.carsharing.model.Car;
import com.danilponomarenko.p2p.carsharing.model.User;
import com.danilponomarenko.p2p.carsharing.repository.CarRepository;
import com.danilponomarenko.p2p.carsharing.service.CarVerificationRequestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CarRepository carRepository;
    private final UserMapper userMapper;
    private final CarVerificationRequestService carVerificationRequestService;

    @GetMapping("/{car_id}/owner")
    public UserResponseDto getUserInfoByCarId(@PathVariable("car_id") Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Car not found user with car_id: " + carId
                ));
        User owner = car.getOwner();
        return userMapper.toDto(owner);
    }

    @GetMapping("/verification-requests")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CarVerificationRequestDto> getAllVerificationRequests() {
        return carVerificationRequestService.getPendingVerificationRequests();
    }

    @PostMapping("/verification-requests/{carId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public void approveCar(@PathVariable Long carId) {
        carVerificationRequestService.approve(carId);
    }

    @PostMapping("/verification-requests/{carId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public void rejectCar(@PathVariable Long carId) {
        carVerificationRequestService.reject(carId);
    }
}
