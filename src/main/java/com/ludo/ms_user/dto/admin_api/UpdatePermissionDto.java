package com.ludo.ms_user.dto.admin_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePermissionDto {
	@NotBlank(message = "/administration/permission/add : description is missing")
	@Size(max = 255, message = "/administration/permission/add : description is too long")
	private String description;
}
