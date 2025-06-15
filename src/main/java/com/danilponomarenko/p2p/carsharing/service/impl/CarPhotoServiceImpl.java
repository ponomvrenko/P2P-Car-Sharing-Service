package com.danilponomarenko.p2p.carsharing.service.impl;

import com.danilponomarenko.p2p.carsharing.dto.car.photo.CarPhotoDto;
import com.danilponomarenko.p2p.carsharing.mapper.CarPhotoMapper;
import com.danilponomarenko.p2p.carsharing.model.Car;
import com.danilponomarenko.p2p.carsharing.model.CarPhoto;
import com.danilponomarenko.p2p.carsharing.repository.CarPhotoRepository;
import com.danilponomarenko.p2p.carsharing.repository.CarRepository;
import com.danilponomarenko.p2p.carsharing.service.CarPhotoService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CarPhotoServiceImpl implements CarPhotoService {

    private final CarPhotoRepository carPhotoRepository;
    private final CarRepository carRepository;
    private final GcsService gcsService;
    private final CarPhotoMapper carPhotoMapper;

    @Override
    public List<CarPhotoDto> uploadPhotos(Long carId, MultipartFile[] files) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + carId));

        List<CarPhotoDto> result = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String url = gcsService.uploadFile(file, file.getOriginalFilename());
                CarPhoto photo = CarPhoto.builder()
                        .car(car)
                        .url(url)
                        .uploadedAt(LocalDateTime.now())
                        .isPrimary(false)
                        .build();
                carPhotoRepository.save(photo);
                result.add(carPhotoMapper.toDto(photo)); // или toDto(photo)
            } catch (Exception e) {
                // Можно логировать ошибку и продолжить дальше, или сразу бросить:
                throw new RuntimeException(
                        "Не удалось загрузить файл: " + file.getOriginalFilename(), e
                );
            }
        }
        return result;
    }

    @Override
    public List<CarPhotoDto> getPhotosByCar(Long carId) {
        List<CarPhoto> photos = carPhotoRepository.findByCarId(carId);
        List<CarPhotoDto> result = new ArrayList<>();
        for (CarPhoto photo : photos) {
            result.add(carPhotoMapper.toDto(photo));
        }
        return result;
    }

    @Override
    public CarPhotoDto getPrimaryPhoto(Long carId) {
        CarPhoto photo = carPhotoRepository.findByCarIdAndPrimaryTrue(carId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Primary photo not found for car: " + carId
                ));
        return carPhotoMapper.toDto(photo);
    }

    @Override
    public void deletePhoto(Long photoId) {
        CarPhoto photo = carPhotoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Photo not found: " + photoId
                ));
        // Удаляем из облака
        gcsService.deleteFileByUrl(photo.getUrl());
        // Удаляем из БД
        carPhotoRepository.delete(photo);
    }

    @Override
    @Transactional
    public void setPrimary(Long photoId) {
        CarPhoto photo = carPhotoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found: " + photoId));
        // Снимаем isPrimary со всех фото этой машины
        carPhotoRepository.resetPrimaryForCar(photo.getCar().getId());
        // Ставим isPrimary только этому фото
        photo.setPrimary(true);
        carPhotoRepository.save(photo);
    }
}
