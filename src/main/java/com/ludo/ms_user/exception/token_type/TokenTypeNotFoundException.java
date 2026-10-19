package com.ludo.ms_user.exception.token_type;

public class TokenTypeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3912107857229000951L;

	public TokenTypeNotFoundException() {
		super("token type not found");
	}
}
