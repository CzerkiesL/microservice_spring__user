package com.ludo.ms_user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ludo.ms_user.dto.common.ApiResponse;
import com.ludo.ms_user.dto.user_api.UpdateAccountRequestDto;
import com.ludo.ms_user.dto.user_api.UserResponseDto;

import com.ludo.ms_user.service_interface.user.UserServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserServiceInt userService;

	@GetMapping
	@PreAuthorize("hasAuthority('user:readSelf')")
	public ResponseEntity<ApiResponse<UserResponseDto>> getAccount(Authentication authentication) {
		String userId = authentication.getName();
		UserResponseDto data = userService.getAccount(userId);
		return ResponseBuilder.success(HttpStatus.OK, "Account found", data);
	}
	
	
	@PatchMapping
	@PreAuthorize("hasAuthority('user:updateSelf')")
	public ResponseEntity<ApiResponse<UserResponseDto>> updateAccount(Authentication authentication, @Valid @RequestBody UpdateAccountRequestDto body) {
		String userId = authentication.getName();
		UserResponseDto data = userService.updateAccount(userId, body);
		return ResponseBuilder.success(HttpStatus.OK, "Account updated", data);
	}
	
	@PatchMapping("/profile-image")
	@PreAuthorize("hasAuthority('user:updateSelf')")
	public ResponseEntity<ApiResponse<UserResponseDto>> updateImageAccount(Authentication authentication, @RequestParam MultipartFile file) {
		String userId = authentication.getName();
		UserResponseDto data = userService.updateImageAccount(userId, file);
		return ResponseBuilder.success(HttpStatus.OK, "Account image updated", data);
	}
	
	@DeleteMapping("")
	@PreAuthorize("hasAuthority('user:deleteSelf')")
	public ResponseEntity<ApiResponse<String>> deleteAccount(Authentication authentication) {
		String userId = authentication.getName();
		String data = userService.deleteAccount(userId);
		return ResponseBuilder.success(HttpStatus.OK, "Account deleted with success", data);
	}
	
}
