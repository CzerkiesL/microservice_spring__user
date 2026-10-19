package com.ludo.ms_user.exception.session_token;

import java.util.UUID;

public class TokenSessionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3532200759550127269L;
	
	public TokenSessionNotFoundException(UUID userId, String tokenType) {
		super("SessionToken of type " + tokenType + " for " + userId + " not found");
	}
}
