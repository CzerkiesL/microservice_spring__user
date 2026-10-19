package com.ludo.ms_user.controller.administration;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.admin_api.AdminSessionTokenDto;
import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.service_interface.administration.SessionTokenAdminServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administration/session-token")
@RequiredArgsConstructor
public class AdministrationtSessionTokenRestController {
	
	private final SessionTokenAdminServiceInt sessionTokenAdminService;
	
	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyAuthority('sessionToken:crud', 'sessionToken:read')")
	public ResponseEntity<ApiResponse<List<AdminSessionTokenDto>>> getSessionTokenByUsername(@PathVariable String username) {
		List<AdminSessionTokenDto> data = sessionTokenAdminService.getSessionTokenByUsername(username);
		return ResponseBuilder.success(HttpStatus.OK, data.size() + " SessionToken founds for " + username, data);
	}
	
	@GetMapping("/type/{token-type}")
	@PreAuthorize("hasAnyAuthority('sessionToken:crud', 'sessionToken:read')")
	public ResponseEntity<ApiResponse<List<AdminSessionTokenDto>>> getSessionTokenByTokenType(@PathVariable("token-type") String tokenType) {
		List<AdminSessionTokenDto> data = sessionTokenAdminService.getSessionTokenByTokenType(tokenType);
		return ResponseBuilder.success(HttpStatus.OK, data.size() + " SessionToken founds of type " + tokenType, data);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('sessionToken:crud', 'sessionToken:read')")
	public ResponseEntity<ApiResponse<List<AdminSessionTokenDto>>> getAllSessionToken() {
		List<AdminSessionTokenDto> data = sessionTokenAdminService.getAllSessionToken();
		return ResponseBuilder.success(HttpStatus.OK, data.size() + " SessionToken founds", data);
	}
	
	@PostMapping("/generate/{token-type}")
	@PreAuthorize("hasAnyAuthority('sessionToken:crud', 'sessionToken:create')")
	public ResponseEntity<ApiResponse<AdminSessionTokenDto>> generateAndSendSessionToken(@PathVariable("token-type") String tokenType, @RequestParam String username) {
		AdminSessionTokenDto data = sessionTokenAdminService.generateAndSendSessionToken(username, tokenType);
		return ResponseBuilder.success(HttpStatus.OK, "SessionToken genrate and send", data);
	}
	
	@PatchMapping("/revoke/{token-id}")
	@PreAuthorize("hasAnyAuthority('sessionToken:crud', 'sessionToken:update')")
	public ResponseEntity<ApiResponse<AdminSessionTokenDto>> revokeSessionToken(@PathVariable("token-id") String tokenId ) {
		AdminSessionTokenDto data = sessionTokenAdminService.revokeSessionToken(tokenId);
		return ResponseBuilder.success(HttpStatus.OK, "SessionToken revoked", data);
	}
	
	@DeleteMapping("/clear")
	@PreAuthorize("hasAnyAuthority('sessionToken:crud', 'sessionToken:delete')")
	public ResponseEntity<ApiResponse<String>> clearSessionToken() {
		String data = sessionTokenAdminService.clearSessionToken();
		return ResponseBuilder.success(HttpStatus.OK, "SessionToken clear", data);
	}
}
