package com.ludo.ms_user.exception.global;

public class InvalidAccessTokenException extends RuntimeException {

	private static final long serialVersionUID = -8997724521084555400L;

	public InvalidAccessTokenException() {
		super("Authentication required or access token is invalid");
	}
}
