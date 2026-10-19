package com.ludo.ms_user.service_interface.administration;

import java.util.List;

import com.ludo.ms_user.dto.admin_api.AdminPermissionDto;
import com.ludo.ms_user.dto.admin_api.CreatePermissionDto;
import com.ludo.ms_user.dto.admin_api.UpdatePermissionDto;

public interface PermissionAdminServiceInt {

	AdminPermissionDto getPermissionByCode(String code);

	List<AdminPermissionDto> getAllPermission();
	
	AdminPermissionDto createPermission(CreatePermissionDto body);

	AdminPermissionDto updatePermission(String code, UpdatePermissionDto body);

	String updateCanDeletePermission(String code);

	String deletePermission(String code);

}
