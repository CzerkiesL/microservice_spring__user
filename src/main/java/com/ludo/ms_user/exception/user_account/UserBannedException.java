package com.ludo.ms_user.exception.user_account;

public class UserBannedException extends RuntimeException {

	private static final long serialVersionUID = 267175665302554641L;

	public UserBannedException() {
		super("User banned");
	}
}
