package com.danilponomarenko.p2p.carsharing.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GcpStorageConfig {

    @Bean
    public Storage googleStorage(@Value("${gcp.credentials.path}")
                                     String credentialsPath) throws IOException {
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials
                        .fromStream(new FileInputStream(credentialsPath)))
                .build()
                .getService();
    }
}
