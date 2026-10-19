package com.ludo.ms_user.exception.token_type;


public class TokenTypeAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 4341769548773384507L;

	public TokenTypeAlreadyExistException() {
		super("token type already exist");
	}
}
