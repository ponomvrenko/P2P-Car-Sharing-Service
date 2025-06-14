package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.user.UserResponseDto;
import com.danilponomarenko.p2p.carsharing.dto.user.registration.UserRegistrationRequestDto;
import com.danilponomarenko.p2p.carsharing.exception.EntityNotFoundException;
import com.danilponomarenko.p2p.carsharing.exception.RegistrationException;
import com.danilponomarenko.p2p.carsharing.mapper.UserMapper;
import com.danilponomarenko.p2p.carsharing.model.Role;
import com.danilponomarenko.p2p.carsharing.model.User;
import com.danilponomarenko.p2p.carsharing.repository.RoleRepository;
import com.danilponomarenko.p2p.carsharing.repository.UserRepository;
import com.danilponomarenko.p2p.carsharing.service.UserService;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String CAN_NOT_REGISTER_USER_BY_EMAIL = "Can't register a user by email:";
    private static final String FAILED_FIND_USER_BY_EMAIL = "Can't find user by email ";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(CAN_NOT_REGISTER_USER_BY_EMAIL + requestDto.getEmail());
        }

        String rowRole = requestDto.getRole();
        Role.RoleName roleName;
        try {
            roleName = Role.RoleName.valueOf(rowRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            // TODO: Make this exception more specific
            throw new RuntimeException("Invalid role: " + rowRole);
        }

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found!"));

        User userSave = userMapper.toModel(requestDto);
        if (userSave.getRoles() == null) {
            userSave.setRoles(new HashSet<>());
        }
        userSave.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userSave.getRoles().add(role);

        return userMapper.toDto(userRepository.save(userSave));
    }

    @Override
    public UserResponseDto getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(FAILED_FIND_USER_BY_EMAIL + email));
        return userMapper.toDto(user);
    }
}
