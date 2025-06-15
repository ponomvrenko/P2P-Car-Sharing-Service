package com.danilponomarenko.p2p.carsharing.service;

import com.danilponomarenko.p2p.carsharing.dto.car.photo.CarPhotoDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CarPhotoService {
    List<CarPhotoDto> uploadPhotos(Long carId, MultipartFile[] files);

    List<CarPhotoDto> getPhotosByCar(Long carId);

    CarPhotoDto getPrimaryPhoto(Long carId);

    void deletePhoto(Long photoId);

    void setPrimary(Long photoId);
}
