package com.ludo.ms_user.exception.global;

public class DataCantBeDeletedException extends RuntimeException {

	private static final long serialVersionUID = -3004738195974889547L;
	
	public DataCantBeDeletedException() {
		super("this data is used on the server, it can't be deleted");
	}
}
