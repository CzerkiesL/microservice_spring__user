package com.ludo.ms_user.exception.user_account;

public class UserNotFoundExeception extends RuntimeException {

	private static final long serialVersionUID = 8950700148595922487L;

	public UserNotFoundExeception() {
		super("User not found");
	}
}
