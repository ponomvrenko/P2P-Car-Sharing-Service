package com.danilponomarenko.p2p.carsharing.repository;

import com.danilponomarenko.p2p.carsharing.model.CarPhoto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, Long> {
    @Modifying
    @Query("UPDATE CarPhoto p SET p.isPrimary = false WHERE p.car.id = :carId")
    void resetPrimaryForCar(@Param("carId") Long carId);

    Optional<CarPhoto> findByCarIdAndPrimaryTrue(Long carId);

    List<CarPhoto> findByCarId(Long carId);
}
