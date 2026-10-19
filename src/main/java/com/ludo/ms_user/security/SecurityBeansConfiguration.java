package com.ludo.ms_user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityBeansConfiguration {
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(12);
	}
}
