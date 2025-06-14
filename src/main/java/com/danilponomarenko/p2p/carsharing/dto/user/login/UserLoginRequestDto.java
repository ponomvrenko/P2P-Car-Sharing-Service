package com.danilponomarenko.p2p.carsharing.dto.user.login;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotBlank
        @Length(min = 8, max = 24)
        String email,
        @NotBlank
        @Length(min = 8, max = 24)
        String password
) {
}
