package com.ludo.ms_user.exception.banned_user;

public class BannedUserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -4672504604189787135L;
	
	public BannedUserAlreadyExistException() {
		super("BannedUser already exist");
	}
}
