package com.ludo.ms_user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {
	private String accessSecret;
	private String refreshSecret;
	private Long accessDuration;
	private Long refreshDuration;
	private String issuer;
	private String audience;
}
