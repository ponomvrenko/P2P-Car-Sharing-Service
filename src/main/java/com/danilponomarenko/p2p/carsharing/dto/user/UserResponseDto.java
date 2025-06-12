package com.danilponomarenko.p2p.carsharing.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean isVerified;
}
