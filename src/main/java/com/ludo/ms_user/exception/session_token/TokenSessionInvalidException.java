package com.ludo.ms_user.exception.session_token;

public class TokenSessionInvalidException extends RuntimeException {

	private static final long serialVersionUID = 3883218096868436914L;
	
	public TokenSessionInvalidException(String tokenType) {
		super(tokenType + " invalid");
	}

}
