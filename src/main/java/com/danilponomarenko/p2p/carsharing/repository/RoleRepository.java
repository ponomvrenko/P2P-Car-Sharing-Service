package com.danilponomarenko.p2p.carsharing.repository;

import com.danilponomarenko.p2p.carsharing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(Role.RoleName roleName);
}
