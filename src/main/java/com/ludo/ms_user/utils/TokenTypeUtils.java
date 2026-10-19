package com.ludo.ms_user.utils;

import org.springframework.stereotype.Component;

import com.ludo.ms_user.dto.admin_api.UpdateTokenTypeRequestDto;

import com.ludo.ms_user.entity.TokenType;

@Component
public class TokenTypeUtils {
	
	public TokenType checkUpdate(TokenType tokenType, UpdateTokenTypeRequestDto body) {
		
		if (body.getDescription() != null && body.getDescription().trim().length() > 0) {
			tokenType.setDescription(body.getDescription());
		}
		if (body.getTokenExpirationTime() != null && body.getTokenExpirationTime() > 0) {
			tokenType.setTokenExpirationTime(body.getTokenExpirationTime());
		}
		
		return tokenType;
	}
	
}
