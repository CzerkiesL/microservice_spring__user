package com.ludo.ms_user.exception.session_token;

public class TokenRevokedException extends RuntimeException {

	private static final long serialVersionUID = -5746903388504999683L;
	
	public TokenRevokedException() {
		super("This token has been revoked");
	}
}
