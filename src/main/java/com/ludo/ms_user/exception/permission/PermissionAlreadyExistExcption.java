package com.ludo.ms_user.exception.permission;

public class PermissionAlreadyExistExcption extends RuntimeException {

	private static final long serialVersionUID = 9041797430320583779L;

	public PermissionAlreadyExistExcption(String code) {
		super("Permission already exist : " + code);
	}
}
