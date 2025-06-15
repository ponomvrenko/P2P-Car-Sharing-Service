package com.danilponomarenko.p2p.carsharing.controller;

import com.danilponomarenko.p2p.carsharing.dto.car.photo.CarPhotoDto;
import com.danilponomarenko.p2p.carsharing.service.CarPhotoService;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/car-photos")
public class CarPhotoController {
    private final CarPhotoService carPhotoService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<CarPhotoDto>> uploadPhotos(
            @RequestParam("carId") Long carId,
            @RequestParam("files") MultipartFile[] files) {
        try {
            List<CarPhotoDto> result = carPhotoService.uploadPhotos(carId, files);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{carId}")
    @PermitAll
    public ResponseEntity<List<CarPhotoDto>> getPhotosByCar(@PathVariable Long carId) {
        List<CarPhotoDto> photos = carPhotoService.getPhotosByCar(carId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/primary/{carId}")
    @PermitAll
    public ResponseEntity<CarPhotoDto> getPrimaryPhoto(@PathVariable Long carId) {
        try {
            CarPhotoDto photo = carPhotoService.getPrimaryPhoto(carId);
            return ResponseEntity.ok(photo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{photoId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) {
        try {
            carPhotoService.deletePhoto(photoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/set-primary/{photoId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Void> setPrimary(@PathVariable Long photoId) {
        carPhotoService.setPrimary(photoId);
        return ResponseEntity.ok().build();
    }
}

