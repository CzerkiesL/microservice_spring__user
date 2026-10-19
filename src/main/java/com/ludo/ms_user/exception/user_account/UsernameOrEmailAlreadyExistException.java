package com.ludo.ms_user.exception.user_account;

public class UsernameOrEmailAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 4777985478623165911L;

	public UsernameOrEmailAlreadyExistException() {
		super("username or email already exist");
	}
}
