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
public class AdminSessionTokenDto {
	private UUID tokenId;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant expirationDate;
	private String tokenType;
	private String user;
	private boolean consumed;
	private boolean revoked;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant createdAt;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant updatedAt;
}
