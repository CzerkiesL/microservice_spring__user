package com.ludo.ms_user.dto.auth_api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
	
	@NotBlank(message = "/register : username is missing.")
	@Pattern(regexp = "[a-zA-Z0-9_]{3,20}$", message = "/register : username is not valid.")
	private String username;
	
	@NotBlank(message = "/register : email is missing.")
	@Email(message = "/register : email is not valid.")
	@Size(max = 254, message = "/register : email is too long.")
	private String email;
	
	@NotBlank(message = "/register : password is missing.")
	@Size(min = 15,  max = 128, message = "/register : password must have between 15 and 128 characters.")
	private String password;
	
	@NotBlank(message = "/register : confirmPassword is missing.")
	@Size(min = 15,  max = 128, message = "/register : confirmPassword must have between 15 and 128 characters.")
	private String confirmPassword;
}
