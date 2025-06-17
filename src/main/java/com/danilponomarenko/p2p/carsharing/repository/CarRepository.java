package com.danilponomarenko.p2p.carsharing.repository;

import com.danilponomarenko.p2p.carsharing.model.Car;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(Car.CarStatus status);

    List<Car> findByOwnerEmail(String email);

    List<Car> findCarByStatus(Car.CarStatus status);
}
