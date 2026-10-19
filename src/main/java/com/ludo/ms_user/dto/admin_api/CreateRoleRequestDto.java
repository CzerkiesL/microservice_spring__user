package com.ludo.ms_user.dto.admin_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoleRequestDto {
	
	@NotBlank(message = "/administration/role/add : name is required")
	@Size(max = 50, message = "/administration/role/add : name too long")
	private String name;
	
	@NotBlank(message = "/administration/role/add : description is required")
	@Size(max = 255, message = "/administration/role/add : description too long")
	private String description;
}
