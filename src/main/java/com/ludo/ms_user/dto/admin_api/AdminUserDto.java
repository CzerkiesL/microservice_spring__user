package com.ludo.ms_user.dto.admin_api;

import java.time.Instant;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserDto {
	private UUID userId;
	private String Username;
	private String email;
	private String firstname;
	private String lastname;
	private String country;
	private String city;
	private String profileImageUrl;
	private List<String> roles;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant createdAt;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant updatedAt;
	private boolean banned;
	private boolean enabled;
}
