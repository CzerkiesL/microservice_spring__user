package com.ludo.ms_user.exception.http_request;

public class CreationFailedException extends RuntimeException {

	private static final long serialVersionUID = -4063647897236295063L;
	
	public CreationFailedException(String message) {
		super(message);
	}
}
