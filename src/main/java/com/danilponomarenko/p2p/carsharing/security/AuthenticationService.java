package com.danilponomarenko.p2p.carsharing.security;

import com.danilponomarenko.p2p.carsharing.dto.user.login.UserLoginRequestDto;
import com.danilponomarenko.p2p.carsharing.dto.user.login.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", "")) // Якщо роль з префіксом
                .orElse("CUSTOMER");

        String token = jwtUtil.generateToken(authentication.getName(), role);

        return new UserLoginResponseDto(token);
    }
}
