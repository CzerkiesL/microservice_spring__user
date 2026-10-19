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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.admin_api.AdminPermissionDto;
import com.ludo.ms_user.dto.admin_api.CreatePermissionDto;
import com.ludo.ms_user.dto.admin_api.UpdatePermissionDto;

import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.service_interface.administration.PermissionAdminServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administration/permission")
@RequiredArgsConstructor
public class AdministrationPermissionRestController {
	
	private final PermissionAdminServiceInt permissionAdminService;
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('permission:crud', 'permission:create')")
	public ResponseEntity<ApiResponse<AdminPermissionDto>> createPermission(@Valid @RequestBody CreatePermissionDto body) {
		AdminPermissionDto data = permissionAdminService.createPermission(body);
		return ResponseBuilder.success(HttpStatus.CREATED, "Permission created", data);
	}
	
	@GetMapping("/{code}")
	@PreAuthorize("hasAnyAuthority('permission:crud', 'permission:read')")
	public ResponseEntity<ApiResponse<AdminPermissionDto>> getPermissionByCode(@PathVariable String code) {
		AdminPermissionDto data = permissionAdminService.getPermissionByCode(code);
		return ResponseBuilder.success(HttpStatus.OK, "Permission found", data);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('permission:crud', 'permission:read')")
	public ResponseEntity<ApiResponse<List<AdminPermissionDto>>> getAllPermission() {
		List<AdminPermissionDto> data = permissionAdminService.getAllPermission();
		return ResponseBuilder.success(HttpStatus.OK, "Permissions found", data);
	}
	
	@PatchMapping("/{code}")
	@PreAuthorize("hasAnyAuthority('permission:crud', 'permission:update')")
	public ResponseEntity<ApiResponse<AdminPermissionDto>> updatePermission(@PathVariable String code, @Valid @RequestBody UpdatePermissionDto body) {
		AdminPermissionDto data = permissionAdminService.updatePermission(code, body);
		return ResponseBuilder.success(HttpStatus.OK, "Permission updated", data);
	}
	
	@PatchMapping("/{code}/can-delete")
	@PreAuthorize("hasAnyAuthority('permission:crud', 'permission:update') and hasAnyAuthority('permission:crud', 'permission:delete')")
	public ResponseEntity<ApiResponse<String>> updateCanDeletePermission(@PathVariable String code) {
		String data = permissionAdminService.updateCanDeletePermission(code);
		return ResponseBuilder.success(HttpStatus.OK, "Permission updated", data);
	}
	
	@DeleteMapping("/{code}")
	@PreAuthorize("hasAnyAuthority('permission:crud', 'permission:delete')")
	public ResponseEntity<ApiResponse<String>> deletePermission(@PathVariable String code) {
		String data = permissionAdminService.deletePermission(code);
		return ResponseBuilder.success(HttpStatus.OK, "Permission deleted", data);
	}
}
