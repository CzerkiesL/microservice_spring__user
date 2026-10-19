package com.ludo.ms_user.exception.session_token;

public class TokenAlreadyConsumedException extends RuntimeException {

	private static final long serialVersionUID = 2103538160016809847L;

	public TokenAlreadyConsumedException() {
		super("Token already consumed");
	};
}
