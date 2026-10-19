package com.ludo.ms_user.controller.administration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.admin_api.AdminTokenTypeDto;
import com.ludo.ms_user.dto.admin_api.CreateTokenTypeRequestDto;
import com.ludo.ms_user.dto.admin_api.UpdateTokenTypeRequestDto;
import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.service_interface.administration.TokenTypeAdminServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/administration/token-type")
@RequiredArgsConstructor
public class AdministrationtTokenTypeRestController {
	
	private final TokenTypeAdminServiceInt tokenTypeAdminService;

	@PostMapping
	@PreAuthorize("hasAnyAuthority('tokenType:crud', 'tokenType:create')")
	public ResponseEntity<ApiResponse<AdminTokenTypeDto>> createTokenType(@Valid @RequestBody CreateTokenTypeRequestDto body) {
		AdminTokenTypeDto data = tokenTypeAdminService.createTokenType(body);
		return ResponseBuilder.success(HttpStatus.CREATED, "TokenType created", data);
	}
	
	@GetMapping("/{name}")
	@PreAuthorize("hasAnyAuthority('tokenType:crud', 'tokenType:read')")
	public ResponseEntity<ApiResponse<AdminTokenTypeDto>> getTokenTypeByName(@PathVariable String name) {
		AdminTokenTypeDto data = tokenTypeAdminService.getTokenTypeByName(name);
		return ResponseBuilder.success(HttpStatus.OK, "TokenType found", data);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('tokenType:crud', 'tokenType:read')")
	public ResponseEntity<ApiResponse<List<AdminTokenTypeDto>>> getAllTokenType() {
		List<AdminTokenTypeDto> data = tokenTypeAdminService.getAllTokenType();
		return ResponseBuilder.success(HttpStatus.OK, "TokenTypes found", data);
	}
	
	@PatchMapping("/{name}")
	@PreAuthorize("hasAnyAuthority('tokenType:crud', 'tokenType:update')")
	public ResponseEntity<ApiResponse<AdminTokenTypeDto>> updateTokenType(@PathVariable String name, @Valid @RequestBody UpdateTokenTypeRequestDto body) {
		AdminTokenTypeDto data = tokenTypeAdminService.updateTokenType(name, body);
		return ResponseBuilder.success(HttpStatus.OK, "TokenType updated", data);
	}
	
	@PatchMapping("/{name}/can-delete")
	@PreAuthorize("hasAnyAuthority('tokenType:crud', 'tokenType:update') and hasAnyAuthority('tokenType:crud', 'tokenType:delete')")
	public ResponseEntity<ApiResponse<String>> updateCanDeleteTokenType(@PathVariable String name) {
		String data = tokenTypeAdminService.updateCanDeleteTokenType(name);
		return ResponseBuilder.success(HttpStatus.OK, "TokenType updated", data);
	}
	
	@DeleteMapping("/{name}")
	@PreAuthorize("hasAnyAuthority('tokenType:crud', 'tokenType:delete')")
	public ResponseEntity<ApiResponse<String>> deleteTokenType(@PathVariable String name) {
		String data = tokenTypeAdminService.deleteTokenType(name);
		return ResponseBuilder.success(HttpStatus.OK, "TokenType deleted", data);
	}
}
