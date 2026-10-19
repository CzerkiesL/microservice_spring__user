package com.ludo.ms_user.exception.global;

public class InvalidRefreshTokenException extends RuntimeException {

	private static final long serialVersionUID = -8997724521084555400L;

	public InvalidRefreshTokenException() {
		super("Authentication required or refresh token is invalid");
	}
}
