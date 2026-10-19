package com.ludo.ms_user.dto.auth_api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskResetPasswordRequestDto {
	
	@NotBlank(message = "/ask-reset-password : email is required")
	@Email(message = "/ask-reset-password : email is not valid.")
	@Size(max = 254, message = "/ask-reset-password : email is too long.")
	private String email;
	
}
