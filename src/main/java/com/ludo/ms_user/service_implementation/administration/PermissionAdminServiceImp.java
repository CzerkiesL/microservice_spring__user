package com.ludo.ms_user.service_implementation.administration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.dto.admin_api.AdminPermissionDto;
import com.ludo.ms_user.dto.admin_api.CreatePermissionDto;
import com.ludo.ms_user.dto.admin_api.UpdatePermissionDto;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;

import com.ludo.ms_user.exception.global.DataCantBeDeletedException;

import com.ludo.ms_user.service_interface.PermissionServiceInt;
import com.ludo.ms_user.service_interface.RoleServiceInt;

import com.ludo.ms_user.service_interface.administration.PermissionAdminServiceInt;

import com.ludo.ms_user.utils.AdminDtoBuilder;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionAdminServiceImp implements PermissionAdminServiceInt {
	
	private final PermissionServiceInt permissionService;
	private final RoleServiceInt roleService;
	
	private final AdminDtoBuilder adminDtoBuilder;
	

	@Override
	public AdminPermissionDto getPermissionByCode(String code) {
		Permission permission = permissionService.getPermissionByCode(code, true);
		return adminDtoBuilder.formatAdminPermissionDto(permission);
	}

	@Override
	public List<AdminPermissionDto> getAllPermission() {
		List<Permission> permissions = permissionService.getAllPermission();
		List<AdminPermissionDto> permissionsDto = new ArrayList<>();
		for (Permission permission : permissions) { permissionsDto.add(adminDtoBuilder.formatAdminPermissionDto(permission)); }
		return permissionsDto;
	}

	@Override
	public AdminPermissionDto createPermission(CreatePermissionDto body) {
		permissionService.getPermissionByCode(body.getCode(), false);
		
		Permission permission = permissionService.postPermission(Permission.builder()
				.code(body.getCode())
				.description(body.getDescription())
				.build());
		
		return adminDtoBuilder.formatAdminPermissionDto(permission);
	}

	@Override
	public AdminPermissionDto updatePermission(String code, UpdatePermissionDto body) {
		Permission permission = permissionService.getPermissionByCode(code, true);
		permission.setDescription(body.getDescription());
		Permission updatePermission = permissionService.updatePermission(permission);
		return adminDtoBuilder.formatAdminPermissionDto(updatePermission);
	}
	
	@Override
	public String updateCanDeletePermission(String code) {
		Permission permission = permissionService.getPermissionByCode(code, true);
		permission.setCanBeDeleted(!permission.isCanBeDeleted());
		Permission updatePermission = permissionService.updatePermission(permission);
		return updatePermission.isCanBeDeleted() ?
				"Permission : " + code + " now can be deleted" : "Permission : " + code + " now can't be deleted";
	}

	@Override
	@Transactional
	public String deletePermission(String code) {
		Permission permission = permissionService.getPermissionByCode(code, true);
		if (!permission.isCanBeDeleted()) { throw new DataCantBeDeletedException(); }
		
		List<Role> roles = roleService.getRoleByPermissionContaining(permission);
		
		for (Role role : roles) {
			role.getPermissions().remove(permission);
		}
		
		return permissionService.deletePermission(permission);
	}
}
