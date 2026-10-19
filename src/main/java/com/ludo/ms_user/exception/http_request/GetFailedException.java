package com.ludo.ms_user.exception.http_request;

public class GetFailedException extends RuntimeException {

	private static final long serialVersionUID = -3027844772578247205L;

	public GetFailedException(String message) {
		super(message);
	}
}
