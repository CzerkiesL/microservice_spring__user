package com.ludo.ms_user.exception.user_profile;

public class UserProfileAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 2730234881295925189L;

	public UserProfileAlreadyExistException() {
		super("UserProfile already exist");
	}
}
