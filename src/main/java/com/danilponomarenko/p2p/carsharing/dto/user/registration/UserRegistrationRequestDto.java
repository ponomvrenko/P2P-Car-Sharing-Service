package com.danilponomarenko.p2p.carsharing.dto.user.registration;

import com.danilponomarenko.p2p.carsharing.validation.FieldMatchRegistration;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Accessors(chain = true)
@FieldMatchRegistration
public class UserRegistrationRequestDto {
    private static final String NOT_BE_NULL = "Can't be null";
    private static final String FROM_8_TO_24_CHARACTERS = "Must contain 8 to 24 characters long";
    private static final String INCORRECT_EMAIL_FORMAT = "Incorrect email format";

    @Email(message = INCORRECT_EMAIL_FORMAT)
    @NotNull(message = NOT_BE_NULL)
    private String email;

    @NotNull(message = NOT_BE_NULL)
    @Length(min = 8, max = 24, message = FROM_8_TO_24_CHARACTERS)
    private String password;

    @NotNull(message = NOT_BE_NULL)
    @Length(min = 8, max = 24, message = FROM_8_TO_24_CHARACTERS)
    private String repeatPassword;

    @NotNull(message = NOT_BE_NULL)
    private String firstName;

    @NotNull(message = NOT_BE_NULL)
    private String lastName;

    @NotNull(message = NOT_BE_NULL)
    private String phoneNumber;

    @NotNull(message = "Role must be specified")
    private String role; // RoleName — enum с CUSTOMER (0), OWNER (1), ADMIN (2) и т.п.

}
