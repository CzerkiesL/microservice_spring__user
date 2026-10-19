package com.ludo.ms_user.service_interface;

import java.util.List;

import com.ludo.ms_user.entity.TokenType;

public interface TokenTypeServiceInt {
	
	TokenType getTokenTypeByName(String name, boolean get);
	
	List<TokenType> getAllTokenType();
	
	TokenType postTokenType(TokenType tokenType);
	
	TokenType updateTokenType(TokenType tokenType);
	
	String deleteTokenType(TokenType tokenType);
}
