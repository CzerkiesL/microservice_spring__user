package com.ludo.ms_user.service_interface;

import java.util.List;

import com.ludo.ms_user.entity.Permission;

public interface PermissionServiceInt {
	
	Permission getPermissionByCode(String code, boolean get);
	
	List<Permission> getAllPermission();
	
	Permission postPermission(Permission permission);
	
	Permission updatePermission(Permission permission);
	
	String deletePermission(Permission permission);
	
}
