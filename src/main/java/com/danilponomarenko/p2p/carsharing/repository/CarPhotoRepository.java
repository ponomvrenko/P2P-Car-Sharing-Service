package com.danilponomarenko.p2p.carsharing.repository;

import com.danilponomarenko.p2p.carsharing.model.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPhotoRepository extends JpaRepository<CarPhoto, Long> {
    // можно будет добавить методы типа:
    // List<CarPhoto> findByCarId(Long carId);
}
