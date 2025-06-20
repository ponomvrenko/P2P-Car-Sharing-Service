package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.user.UserResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.user.login.UserLoginRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.user.login.UserLoginResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.user.registration.UserRegistrationRequestDto;
import com.danilponomarenko.p2p.carsharing.exception.RegistrationException;
import com.danilponomarenko.p2p.carsharing.security.AuthenticationService;
import com.danilponomarenko.p2p.carsharing.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management",
        description = "Endpoints for authentication and authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Login to a user account",
            description = "Get a JWT token to a certain account")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user",
            description = "Add a new user with USER roleName to database")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
