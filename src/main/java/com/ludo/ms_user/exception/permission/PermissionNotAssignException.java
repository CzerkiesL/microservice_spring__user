package com.ludo.ms_user.exception.permission;

public class PermissionNotAssignException extends RuntimeException {

	private static final long serialVersionUID = 2811051821607686278L;
	
	public PermissionNotAssignException(String code, String roleName) {
		super("Permission : " + code + " is not assign to " + roleName);
	}
}
