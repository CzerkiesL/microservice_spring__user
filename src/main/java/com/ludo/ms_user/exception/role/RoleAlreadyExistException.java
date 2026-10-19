package com.ludo.ms_user.exception.role;

public class RoleAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 397979520502031497L;
	
	public RoleAlreadyExistException(String name) {
		super("Role already exist : " + name);
	}

}
