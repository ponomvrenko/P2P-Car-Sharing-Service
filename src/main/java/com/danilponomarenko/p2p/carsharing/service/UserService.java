package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.user.UserResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.user.registration.UserRegistrationRequestDto;
import com.danilponomarenko.p2p.carsharing.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto getProfile(String email);
}
