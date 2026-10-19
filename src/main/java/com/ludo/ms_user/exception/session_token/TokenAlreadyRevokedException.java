package com.ludo.ms_user.exception.session_token;

public class TokenAlreadyRevokedException extends RuntimeException {

	private static final long serialVersionUID = 5282345621995515601L;

	public TokenAlreadyRevokedException() {
		super("token is already revoked");
	}
}
