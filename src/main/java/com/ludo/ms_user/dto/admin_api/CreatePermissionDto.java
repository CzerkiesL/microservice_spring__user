package com.ludo.ms_user.dto.admin_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermissionDto {
	
	@NotBlank(message = "/administration/permission/add : code is missing")
	@Size(max = 100, message = "/administration/permission/add : code is too long")
	private String code;
	
	@NotBlank(message = "/administration/permission/add : description is missing")
	@Size(max = 255, message = "/administration/permission/add : description is too long")
	private String description;
}
