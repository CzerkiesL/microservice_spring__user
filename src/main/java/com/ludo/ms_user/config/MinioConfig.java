package com.ludo.ms_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
	
	private final MinioProperties minioProperties;
	
	@Bean
	MinioClient minioInternalClient() {
		return MinioClient.builder()
			.endpoint(minioProperties.getInternalEndpoint())
			.credentials(
				minioProperties.getAccessKey(),
				minioProperties.getSecretKey()
			)
			.build();
	}
	
	@Bean
	MinioClient minioExternalClient() {
		return MinioClient.builder()
			.endpoint(minioProperties.getExternalEndpoint())
			.credentials(
				minioProperties.getAccessKey(),
				minioProperties.getSecretKey()
			)
			.build();
	}
	
}
