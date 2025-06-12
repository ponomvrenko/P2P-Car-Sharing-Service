package com.danilponomarenko.p2p.carsharing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true, length = 30)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return "ROLE_" + roleName.name();
    }

    public enum RoleName {
        CUSTOMER(0), OWNER(1), ADMIN(2);

        private final int index;

        RoleName(int index) {
            this.index = index;
        }

        public Integer getIndex() {
            return index;
        }

        public static RoleName getByIndex(int index) {
            for (RoleName roleName : RoleName.values()) {
                if (roleName.getIndex() == index) {
                    return roleName;
                }
            }
            throw new IllegalArgumentException("No enum constant with index: " + index);
        }
    }
}

