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
public class AdminRoleDto {
	private UUID roleId;
	private String name;
	private String description;
	private List<String> permissions;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant createdAt;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
	private Instant updatedAt;
	private boolean canBeDeleted;
}
