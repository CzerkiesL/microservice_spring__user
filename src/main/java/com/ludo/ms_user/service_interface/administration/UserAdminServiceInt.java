package com.ludo.ms_user.service_interface.administration;

import java.util.List;

import com.ludo.ms_user.dto.admin_api.AdminUserDto;

public interface UserAdminServiceInt {

	AdminUserDto getUserByUsername(String username);

	List<AdminUserDto> getAllUser();

	AdminUserDto banUser(String username, String duration);

	AdminUserDto unbanUser(String username);

	AdminUserDto addRoleUser(String username, String role);

	AdminUserDto removeRoleUser(String username, String role);

}
