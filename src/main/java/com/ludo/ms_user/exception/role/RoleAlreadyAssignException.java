package com.ludo.ms_user.exception.role;

public class RoleAlreadyAssignException extends RuntimeException {

	private static final long serialVersionUID = 5569985045302605707L;
	
	public RoleAlreadyAssignException() {
		super("This role is already assign to this user");
	}
}
