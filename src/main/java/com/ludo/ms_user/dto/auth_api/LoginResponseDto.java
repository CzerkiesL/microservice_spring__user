package com.ludo.ms_user.dto.auth_api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
	private LoginUserAccountDto user;
	private String accessToken;
	private String refreshToken;
}
