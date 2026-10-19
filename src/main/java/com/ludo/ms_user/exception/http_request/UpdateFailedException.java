package com.ludo.ms_user.exception.http_request;

public class UpdateFailedException extends RuntimeException {

	private static final long serialVersionUID = -3601746256228309983L;

	public UpdateFailedException(String message) {
		super(message);
	}
}
