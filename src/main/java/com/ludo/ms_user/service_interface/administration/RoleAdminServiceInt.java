package com.ludo.ms_user.service_interface.administration;

import java.util.List;

import com.ludo.ms_user.dto.admin_api.AdminRoleDto;
import com.ludo.ms_user.dto.admin_api.CreateRoleRequestDto;
import com.ludo.ms_user.dto.admin_api.UpdateRoleRequestDto;

public interface RoleAdminServiceInt {
	
	AdminRoleDto getRoleByName(String name);
	
	AdminRoleDto createRole(CreateRoleRequestDto body);

	List<AdminRoleDto> getAllRoles();

	AdminRoleDto updateRole(String name, UpdateRoleRequestDto body);
	
	String updateCanDeleteRole(String name);

	AdminRoleDto addPermissionRole(String name, String permission);
	
	AdminRoleDto removePermissionRole(String name, String permission);

	String deleteRole(String name);

}
