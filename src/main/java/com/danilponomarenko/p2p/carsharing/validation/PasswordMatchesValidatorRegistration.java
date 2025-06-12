package com.danilponomarenko.p2p.carsharing.validation;

import com.danilponomarenko.p2p.carsharing.dto.user.registration.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchesValidatorRegistration implements
        ConstraintValidator<FieldMatchRegistration,
                UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto requestDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return requestDto.getPassword() != null
                && Objects.equals(requestDto.getPassword(), requestDto.getRepeatPassword());
    }

}
