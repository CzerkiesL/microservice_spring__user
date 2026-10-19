package com.ludo.ms_user.exception.banned_user;

public class BannedUserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8521751234633025702L;

	public BannedUserNotFoundException() {
		super("BannedUser not found");
	}
}
