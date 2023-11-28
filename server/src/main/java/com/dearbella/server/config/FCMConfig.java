package com.dearbella.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    @Value("${fcm.project-id}")
    private String projectId;

    @Value("${fcm.service-account-file}")
    private String serviceAccountFilePath;

    @Bean
    public void initialize() throws IOException {
        //Firebase 프로젝트 정보를 FireBaseOptions에 입력해준다.
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(serviceAccountFilePath).getInputStream()))
                .setProjectId(projectId)
                .build();

        //입력한 정보를 이용하여 initialze 해준다.
        FirebaseApp.initializeApp(options);
    }
}
