package com.ludo.ms_user.exception.http_request;

public class DeleteFailedException extends RuntimeException {

	private static final long serialVersionUID = -2823516428047807583L;

	public DeleteFailedException(String message) {
		super(message);
	}
}
