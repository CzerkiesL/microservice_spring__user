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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ludo.ms_user.dto.admin_api.AdminRoleDto;
import com.ludo.ms_user.dto.admin_api.CreateRoleRequestDto;
import com.ludo.ms_user.dto.admin_api.UpdateRoleRequestDto;

import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.service_interface.administration.RoleAdminServiceInt;

import com.ludo.ms_user.utils.ResponseBuilder;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administration/role")
@RequiredArgsConstructor
public class AdministrationRoleRestController {
	
	private final RoleAdminServiceInt roleAdminService;
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('role:crud', 'role:create')")
	public ResponseEntity<ApiResponse<AdminRoleDto>> createRole(@Valid @RequestBody CreateRoleRequestDto body) {
		AdminRoleDto data = roleAdminService.createRole(body);
		return ResponseBuilder.success(HttpStatus.CREATED, "Role created", data);
	}

	@GetMapping("/{name}")
	@PreAuthorize("hasAnyAuthority('role:crud', 'role:read')")
	public ResponseEntity<ApiResponse<AdminRoleDto>> getRoleByName(@PathVariable String name) {
		AdminRoleDto data = roleAdminService.getRoleByName(name);
		return ResponseBuilder.success(HttpStatus.OK, "Role found", data);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('role:crud', 'role:read')")
	public ResponseEntity<ApiResponse<List<AdminRoleDto>>> getAllRole() {
		List<AdminRoleDto> data = roleAdminService.getAllRoles();
		return ResponseBuilder.success(HttpStatus.OK, "Roles found", data);
	}
	
	@PatchMapping("/{name}")
	@PreAuthorize("hasAnyAuthority('role:crud', 'role:update')")
	public  ResponseEntity<ApiResponse<AdminRoleDto>> updateRole(@PathVariable String name, @Valid @RequestBody UpdateRoleRequestDto body) {
		AdminRoleDto data = roleAdminService.updateRole(name, body);
		return ResponseBuilder.success(HttpStatus.OK, "Role updated", data);
	}
	
	@PatchMapping("/{name}/can-delete")
	@PreAuthorize("hasAnyAuthority('role:crud', 'role:update') and hasAnyAuthority('role:crud', 'role:delete')")
	public  ResponseEntity<ApiResponse<String>> updateCanDeleteRole(@PathVariable String name) {
		String data = roleAdminService.updateCanDeleteRole(name);
		return ResponseBuilder.success(HttpStatus.OK, "Role updated", data);
	}
	
	@PatchMapping("/{name}/add-permission")
	@PreAuthorize("hasAnyAuthority('role:managePermission')")
	public  ResponseEntity<ApiResponse<AdminRoleDto>> addPermissionRole(@PathVariable String name, @RequestParam String permission) {
		AdminRoleDto data = roleAdminService.addPermissionRole(name, permission);
		return ResponseBuilder.success(HttpStatus.OK, "Role updated", data);
	}
	
	@PatchMapping("/{name}/remove-permission")
	@PreAuthorize("hasAnyAuthority('role:managePermission')")
	public  ResponseEntity<ApiResponse<AdminRoleDto>> removePermissionRole(@PathVariable String name, @RequestParam String permission) {
		AdminRoleDto data = roleAdminService.removePermissionRole(name, permission);
		return ResponseBuilder.success(HttpStatus.OK, "Role updated", data);
	}
	
	@DeleteMapping("/{name}")
	@PreAuthorize("hasAnyAuthority('role:crud', 'role:delete')")
	public  ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable String name) {
		String data = roleAdminService.deleteRole(name);
		return ResponseBuilder.success(HttpStatus.OK, "Role deleted", data);
	}
}
