package com.danilponomarenko.p2p.carsharing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Column(name = "actual_return_date")
    private LocalDateTime actualReturnDate;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @ManyToOne
    @JoinColumn(name = "pick_up_location_id", nullable = false)
    private Location pickUpLocation;

    @ManyToOne
    @JoinColumn(name = "drop_off_location_id", nullable = false)
    private Location dropOffLocation;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RentalStatus status;

    public enum RentalStatus {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELED,
        COMPLETED
    }
}

