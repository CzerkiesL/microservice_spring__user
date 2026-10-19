package com.ludo.ms_user.controller.administration;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.admin_api.AdminBannedUserDto;
import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.service_interface.administration.BannedUserAdminServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administration/banned-user")
@RequiredArgsConstructor
public class AdministrationBannedUserRestController {

	private final BannedUserAdminServiceInt bannedUserAdminService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('bannedUser:read', 'bannedUser:crud')")
	public ResponseEntity<ApiResponse<List<AdminBannedUserDto>>> getAllBannedUser() {
		List<AdminBannedUserDto> data = bannedUserAdminService.getAllBannedUser();
		return ResponseBuilder.success(HttpStatus.OK, data.size() + " BannedUser founds", data);
	}
	
	@GetMapping("/{banId}")
	@PreAuthorize("hasAnyAuthority('bannedUser:read', 'bannedUser:crud')")
	public ResponseEntity<ApiResponse<AdminBannedUserDto>> getBannedUserById(@PathVariable String banId) {
		AdminBannedUserDto data = bannedUserAdminService.getBannedUserByBanId(banId);
		return ResponseBuilder.success(HttpStatus.OK, "BannedUser founds", data);
	}
	
	@PatchMapping("/{banId}")
	@PreAuthorize("hasAnyAuthority('bannedUser:update', 'bannedUser:crud')")
	public ResponseEntity<ApiResponse<AdminBannedUserDto>> updateBannedUserDuration(@PathVariable String banId, @RequestParam String duration) {
		AdminBannedUserDto data = bannedUserAdminService.updateBannedUserDuration(banId, duration);
		return ResponseBuilder.success(HttpStatus.OK, "BannedUser updated", data);
	}
	
	@DeleteMapping
	@PreAuthorize("hasAnyAuthority('bannedUser:delete', 'bannedUser:crud')")
	public ResponseEntity<ApiResponse<String>> clearBannedUser() {
		String data = bannedUserAdminService.clearBannedUser();
		return ResponseBuilder.success(HttpStatus.OK, "BannedUser founds", data);
	}
}
