package com.ludo.ms_user.exception.role;

public class RoleNotAssignException extends RuntimeException {

	private static final long serialVersionUID = -2461957530983982455L;
	
	public RoleNotAssignException() {
		super("This role is not assign to this user");
	}
}
