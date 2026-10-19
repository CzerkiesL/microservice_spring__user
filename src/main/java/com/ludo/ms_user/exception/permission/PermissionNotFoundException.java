package com.ludo.ms_user.exception.permission;

public class PermissionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -408635352101436408L;

	public PermissionNotFoundException(String code) {
		super("Permission not found : " + code);
	}
}
