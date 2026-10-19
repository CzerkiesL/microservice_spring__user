package com.ludo.ms_user.dto.admin_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTokenTypeRequestDto {
	
	@NotBlank(message = "/token-type/add : name is required")
	@Size(max = 25, message = "/token-type/add : name must have 25 character")
	private String name;
	
	@Size(max = 255)
	private String description;
	
	@NotNull(message = "/token-type/add : tokenExpirationTime is required")
	private Long tokenExpirationTime;
}
