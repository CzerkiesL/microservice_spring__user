package com.ludo.ms_user.service_implementation.administration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.dto.admin_api.AdminRoleDto;
import com.ludo.ms_user.dto.admin_api.CreateRoleRequestDto;
import com.ludo.ms_user.dto.admin_api.UpdateRoleRequestDto;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.UserAccount;

import com.ludo.ms_user.exception.global.DataCantBeDeletedException;
import com.ludo.ms_user.exception.permission.PermissionAlreafyAssignException;
import com.ludo.ms_user.exception.permission.PermissionNotAssignException;

import com.ludo.ms_user.service_interface.PermissionServiceInt;
import com.ludo.ms_user.service_interface.RoleServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;

import com.ludo.ms_user.service_interface.administration.RoleAdminServiceInt;

import com.ludo.ms_user.utils.AdminDtoBuilder;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleAdminServiceImp implements RoleAdminServiceInt {
	
	private final RoleServiceInt roleService;
	private final PermissionServiceInt permissionService;
	private final UserAccountServiceInt userAccountService;
	
	private final AdminDtoBuilder adminDtoBuilder;
	
	@Override
	@Transactional
	public AdminRoleDto getRoleByName(String name) {
		Role role = roleService.getRoleByname(name, true);
		return adminDtoBuilder.formatAdminRoleDto(role);
	}

	@Override
	@Transactional
	public List<AdminRoleDto> getAllRoles() {
		List<Role> roles = roleService.getAllRole();
		List<AdminRoleDto> rolesDto = new ArrayList<>();
		for (Role role : roles) {rolesDto.add(adminDtoBuilder.formatAdminRoleDto(role));}
		return rolesDto;
	}
	
	@Override
	@Transactional
	public AdminRoleDto createRole(CreateRoleRequestDto body) {
		roleService.getRoleByname(body.getName(), false);
		Role role = roleService.postRole(body.getName().toUpperCase(), body.getDescription());
		return adminDtoBuilder.formatAdminRoleDto(role);
	}

	@Override
	@Transactional
	public AdminRoleDto updateRole(String name, UpdateRoleRequestDto body) {
		Role role = roleService.getRoleByname(name, true);
		role.setDescription(body.getDescription());
		Role updatedRole = roleService.updateRole(role);
		return adminDtoBuilder.formatAdminRoleDto(updatedRole);
	}
	
	@Override
	public String updateCanDeleteRole(String name) {
		Role role = roleService.getRoleByname(name, true);
		role.setCanBeDeleted(!role.isCanBeDeleted());
		Role updatedRole = roleService.updateRole(role);
		return updatedRole.isCanBeDeleted() ?
				"Role : " + name + " now can be deleted" : "Role : " + name + " now can't be deleted";
	}
	
	@Override
	@Transactional
	public AdminRoleDto addPermissionRole(String name, String permission) {
		Role role = roleService.getRoleByname(name, true);
		Permission savedPermission = permissionService.getPermissionByCode(permission, true);
		if (role.getPermissions().contains(savedPermission)) {
			throw new PermissionAlreafyAssignException(savedPermission.getCode(), name);
		}
		role.getPermissions().add(savedPermission);
		return adminDtoBuilder.formatAdminRoleDto(role);
	}
	
	@Override
	@Transactional
	public AdminRoleDto removePermissionRole(String name, String permission) {
		Role role = roleService.getRoleByname(name, true);
		Permission savedPermission = permissionService.getPermissionByCode(permission, true);
		if (!role.getPermissions().contains(savedPermission)) {
			throw new PermissionNotAssignException(savedPermission.getCode(), name);
		}
		role.getPermissions().remove(savedPermission);
		return adminDtoBuilder.formatAdminRoleDto(role);
	}

	@Override
	@Transactional
	public String deleteRole(String name) {
		Role role = roleService.getRoleByname(name, true);
		if (!role.isCanBeDeleted()) { throw new DataCantBeDeletedException(); }
		
		List<UserAccount> userAccounts = userAccountService.getUserAccountByRoleContaining(role);
		
		for (UserAccount userAccount : userAccounts) {
			userAccount.getRoles().remove(role);
		}
		
		return roleService.deleteRole(role);
	}
}
