package com.danilponomarenko.p2p.carsharing.service.impl;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GcsService {

    private final Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    public GcsService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(
            MultipartFile file,
            String objectName,
            Long uniqueNumber
    ) throws Exception {
        String fileNameInBucket = String.format("%d-%s", uniqueNumber, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileNameInBucket).build();
        storage.create(blobInfo, file.getBytes());
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileNameInBucket);
    }

    public void deleteFileByUrl(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }

        // Пример ссылки: https://storage.googleapis.com/bucket_name/object_name
        String prefix = "https://storage.googleapis.com/" + bucketName + "/";
        if (!url.startsWith(prefix)) {
            throw new IllegalArgumentException("URL does not match GCS bucket: " + url);
        }
        // Получаем objectName — часть после /bucket_name/
        String objectName = url.substring(prefix.length());

        // Удаляем файл из бакета
        boolean deleted = storage.delete(bucketName, objectName);

        if (!deleted) {
            throw new RuntimeException("Failed to delete file from GCS: " + objectName);
        }
    }
}
