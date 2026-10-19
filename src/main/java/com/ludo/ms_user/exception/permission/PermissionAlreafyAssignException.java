package com.ludo.ms_user.exception.permission;

public class PermissionAlreafyAssignException extends RuntimeException {

	private static final long serialVersionUID = 5163117948507399922L;

	public PermissionAlreafyAssignException(String code, String roleName) {
		super("Permission : " + code + " is already assign to " + roleName);
	}
}
