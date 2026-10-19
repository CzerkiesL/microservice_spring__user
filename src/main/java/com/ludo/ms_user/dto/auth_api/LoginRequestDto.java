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
public class LoginRequestDto {
	
	@Pattern(regexp = "[a-zA-Z0-9_]{0,20}$", message = "/login : username is not valid.")
	private String username;
	
	@Email(message = "/login : email is not valid.")
	@Size(max = 254, message = "/login : email is too long.")
	private String email;
	
	@NotBlank(message = "/login : password is missing.")
	@Size(min = 15,  max = 128, message = "/login : password must have between 15 and 128 characters.")
	private String password;
	
}
