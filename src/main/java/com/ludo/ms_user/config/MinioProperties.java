package com.ludo.ms_user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
	private String internalEndpoint;
	private String externalEndpoint;
	private String accessKey;
	private String secretKey;
	private String bucket;
}
