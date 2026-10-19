package com.ludo.ms_user.controller.administration;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.admin_api.AdminUserDto;
import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.service_interface.administration.UserAdminServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administration/user")
@RequiredArgsConstructor
public class AdministrationUserRestController {

	private final UserAdminServiceInt UserAdminService;
	
	@GetMapping("/{username}")
	@PreAuthorize("hasAnyAuthority('user:crud', 'user:readAll')")
	public ResponseEntity<ApiResponse<AdminUserDto>> getUserByUsername(@PathVariable String username) {
		AdminUserDto data = UserAdminService.getUserByUsername(username);
		return ResponseBuilder.success(HttpStatus.OK, "All user found", data);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('user:crud', 'user:readAll')")
	public ResponseEntity<ApiResponse<List<AdminUserDto>>> getAllUser() {
		List<AdminUserDto> data = UserAdminService.getAllUser();
		return ResponseBuilder.success(HttpStatus.OK, "All user found", data);
	}
	
	@PatchMapping("/{username}/ban")
	@PreAuthorize("hasAnyAuthority('user:crud', 'user:ban')")
	public ResponseEntity<ApiResponse<AdminUserDto>> banUser(@PathVariable String username, @RequestParam String duration) {
		AdminUserDto data = UserAdminService.banUser(username, duration);
		return ResponseBuilder.success(HttpStatus.OK, "All user found", data);
	}
	
	@PatchMapping("/{username}/unban")
	@PreAuthorize("hasAnyAuthority('user:crud', 'user:unban')")
	public ResponseEntity<ApiResponse<AdminUserDto>> unbanUser(@PathVariable String username) {
		AdminUserDto data = UserAdminService.unbanUser(username);
		return ResponseBuilder.success(HttpStatus.OK, "All user found", data);
	}
	
	@PatchMapping("/{username}/add-role")
	@PreAuthorize("hasAnyAuthority('user:manageRole')")
	public ResponseEntity<ApiResponse<AdminUserDto>> addRoleUser(@PathVariable String username, @RequestParam String role) {
		AdminUserDto data = UserAdminService.addRoleUser(username, role);
		return ResponseBuilder.success(HttpStatus.OK, "All user found", data);
	}
	
	@PatchMapping("/{username}/remove-role")
	@PreAuthorize("hasAnyAuthority('user:manageRole')")
	public ResponseEntity<ApiResponse<AdminUserDto>> removeRoleUser(@PathVariable String username, @RequestParam String role) {
		AdminUserDto data = UserAdminService.removeRoleUser(username, role);
		return ResponseBuilder.success(HttpStatus.OK, "All user found", data);
	}
}
