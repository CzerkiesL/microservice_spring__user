package com.ludo.ms_user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.dto.auth_api.AskResetPasswordRequestDto;
import com.ludo.ms_user.dto.auth_api.ConfirmAccountRequestDto;
import com.ludo.ms_user.dto.auth_api.LoginRequestDto;
import com.ludo.ms_user.dto.auth_api.LoginResponseDto;
import com.ludo.ms_user.dto.auth_api.RegisterRequestDto;
import com.ludo.ms_user.dto.auth_api.ResendConfirmTokenRequestDto;
import com.ludo.ms_user.dto.auth_api.ResetPasswordRequestDto;

import com.ludo.ms_user.service_interface.authentification.AuthentificationServiceInt;

import com.ludo.ms_user.utils.JwtPairs;
import com.ludo.ms_user.utils.ResponseBuilder;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {
	
	private final AuthentificationServiceInt authentificationService;

	private HttpHeaders prepareHttpHeaders(String refreshToken, Duration cookieMaxAge) {

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
			.path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .maxAge(cookieMaxAge)
            .build();
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
		return httpHeaders;
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequestDto body) {
		String data = authentificationService.register(body);
		return ResponseBuilder.success(HttpStatus.CREATED, "User registered", data);
	}
	
	@PostMapping("/confirm-account")
	public ResponseEntity<ApiResponse<String>> confirmAccount(@Valid @RequestBody ConfirmAccountRequestDto body) {
		String data = authentificationService.confirmAccount(body);
		return ResponseBuilder.success(HttpStatus.ACCEPTED, "User confirmed", data);
	}
	
	@PostMapping("/resend-confirm-token")
	public ResponseEntity<ApiResponse<String>> resendConfirmToken(@Valid @RequestBody ResendConfirmTokenRequestDto body) {
		String data = authentificationService.resendConfirmToken(body);
		return ResponseBuilder.success(HttpStatus.OK, "Activation token resent", data);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto body) {
		LoginResponseDto data = authentificationService.login(body);
		
		LoginResponseDto dataFormated = LoginResponseDto.builder()
			.user(data.getUser())
			.accessToken(data.getAccessToken())
			.refreshToken("no refresh token available")
			.build();
		
		ApiResponse<LoginResponseDto> response = ApiResponse.<LoginResponseDto>builder()
			.status("success")
			.message("User logged")
			.data(dataFormated)
			.errors(null)
			.build();

		return ResponseEntity.status(HttpStatus.OK).headers(prepareHttpHeaders(data.getRefreshToken(), Duration.ofHours(1))).body(response);
	}
	

	@PostMapping("/refresh-session")
	public ResponseEntity<ApiResponse<Map<String, String>>> refresshSession(@CookieValue(required = false) String refreshToken) {
		JwtPairs data = authentificationService.refreshSession(refreshToken);
		
		Map<String, String> accessToken = new HashMap<>();
		accessToken.put("accessToken", data.getAccessToken());
		
		ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
			.status("success")
			.message("Token refreshed")
			.data(accessToken)
			.errors(null)
			.build();
		
		return ResponseEntity.status(HttpStatus.OK).headers(prepareHttpHeaders(data.getRefreshToken(), Duration.ofHours(1))).body(response);
	}
	
	@PostMapping("/ask-reset-password")
	public ResponseEntity<ApiResponse<String>> askResetPassword(@Valid @RequestBody AskResetPasswordRequestDto body) {
		String data = authentificationService.askResetPassword(body);
		return ResponseBuilder.success(HttpStatus.OK, "Rest password email send", data);
	}
	
	@PatchMapping("/reset-password/{resetPasswordToken}")
	public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordRequestDto body, @PathVariable String resetPasswordToken) {
		String data = authentificationService.resetPassword(body, resetPasswordToken);
		return ResponseBuilder.success(HttpStatus.OK, "Reset password ok", data);
	}
	
	@PatchMapping("/logout")
	@PreAuthorize("hasAuthority('user:updateSelf')")
	public ResponseEntity<ApiResponse<String>> logout(Authentication authentication) {
		String userId = authentication.getName();
		String data = authentificationService.logout(userId);
		
		ApiResponse<String> response = ApiResponse.<String>builder()
			.status("success")
			.message("User unlogged")
			.data(data)
			.errors(null)
			.build();
		
		return ResponseEntity.status(HttpStatus.OK).headers(prepareHttpHeaders("", Duration.ZERO)).body(response);
	}
	
}
