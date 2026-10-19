package com.ludo.ms_user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "ms-user-config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsUserConfiguration {
	private boolean userAccountAutoEnabled;
	
	private boolean emailSenderIntern;
	private String emailSenderApp;
	
	private String clientAppUrl;
	
	private String superAdminUsername;
	private String superAdminEmail;
	private String superAdminPassword;
	
}
