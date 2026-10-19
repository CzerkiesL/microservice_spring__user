package com.ludo.ms_user.exception.user_profile;

public class UserProfileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1960683122947653833L;

	public UserProfileNotFoundException() {
		super("UserProfile not found");
	}
}
