package com.ludo.ms_user.dto.auth_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequestDto {
	
	@NotBlank(message = "/reset-password : password is missing.")
	@Size(min = 15,  max = 128, message = "/reset-password : password must have between 15 and 128 characters.")
	private String password;
	
	@NotBlank(message = "/reset-password : confirmPassword is missing.")
	@Size(min = 15,  max = 128, message = "/reset-password : confirmPassword must have between 15 and 128 characters.")
	private String confirmPassword;
}
