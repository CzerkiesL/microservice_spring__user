package com.ludo.ms_user.dto.admin_api;

import java.time.Instant;

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
public class AdminBannedUserDto {
	private UUID banId;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant banEndDate;
	private String banEmail;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant createdAt;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant updatedAt;
	
}
