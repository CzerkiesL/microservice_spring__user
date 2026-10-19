package com.ludo.ms_user.dto.auth_api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmAccountRequestDto {
	
	@NotBlank(message = "/confirm-accuont : token is missing.")
	@Pattern(regexp = "\\d+", message = "/confirm-accuont : token must contain only digit")
	private String token;
	
	@NotBlank(message = "/confirm-accuont : username is missing.")
	@Pattern(regexp = "[a-zA-Z0-9_]{3,20}$", message = "/confirm-accuont: username is not valid.")
	private String username;
}
