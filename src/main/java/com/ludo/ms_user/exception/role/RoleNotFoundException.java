package com.ludo.ms_user.exception.role;

public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 212103816758087131L;

	public RoleNotFoundException(String name) {
		super("Role not found : " + name);
	}
}
