package com.ludo.ms_user.service_implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.TokenType;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;
import com.ludo.ms_user.exception.token_type.TokenTypeAlreadyExistException;
import com.ludo.ms_user.exception.token_type.TokenTypeNotFoundException;

import com.ludo.ms_user.repository.TokenTypeRepository;

import com.ludo.ms_user.service_interface.TokenTypeServiceInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenTypeServiceImp implements TokenTypeServiceInt {

	private final TokenTypeRepository tokenTypeRepository;
	
	@Override
	public TokenType getTokenTypeByName(String name, boolean get) {
		
		Optional<TokenType> tokenType = tokenTypeRepository.findByName(name);
		
		if (get) {
			if (tokenType.isEmpty()) { throw new TokenTypeNotFoundException(); }
			return tokenType.get();
		} else {
			if (tokenType.isPresent()) { throw new TokenTypeAlreadyExistException(); }
		}
		return null;
	}

	@Override
	public List<TokenType> getAllTokenType() {
		try {
			return tokenTypeRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of TokenType list : " + e.getMessage());
		}
	}
	
	@Override
	public TokenType postTokenType(TokenType tokenType) {
		try {
			return tokenTypeRepository.save(tokenType);
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of TokenType : " + e.getMessage());
		}
	}

	@Override
	public TokenType updateTokenType(TokenType tokenType)  {
		try {
			return tokenTypeRepository.save(tokenType);
		} catch (Exception e) {
			throw new UpdateFailedException("Error during update of TokenType : " + e.getMessage());
		}
	}

	@Override
	public String deleteTokenType(TokenType tokenType)  {
		try {
			tokenTypeRepository.delete(tokenType);
			return "Token type : " + tokenType.getName() + " delete with success";
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete of TokenType : " + e.getMessage());
		}
	}	
}
