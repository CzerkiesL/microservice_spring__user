package com.ludo.ms_user.service_interface;

import java.util.List;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;

public interface RoleServiceInt {

	Role getRoleByname(String name, boolean get);
	
	List<Role> getAllRole();
	
	List<Role> getRoleByPermissionContaining(Permission permission);
	
	Role postRole(String name, String description);

	Role updateRole(Role role);

	String deleteRole(Role role);
}
