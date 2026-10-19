package com.ludo.ms_user.dto.auth_api;

import jakarta.validation.constraints.Email;
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
public class ResendConfirmTokenRequestDto {
	
	@NotBlank(message = "/resend-confirm-token : email is missing.")
	@Email(message = "/resend-confirm-token : email is not valid.")
	@Size(max = 254, message = "/resend-confirm-token : email is too long.")
	private String email;
}
