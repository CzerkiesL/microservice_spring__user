package com.ludo.ms_user.exception.user_account;

public class UserAlreadyEnabledException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserAlreadyEnabledException() {
		super("User already enabled");
	}
}
