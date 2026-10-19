package com.ludo.ms_user.exception.user_account;

public class PasswordNotCorrespondException extends RuntimeException {

	private static final long serialVersionUID = 254307913750765122L;
	
	public PasswordNotCorrespondException() {
		super("password and confirm password must be the same");
	}
}
