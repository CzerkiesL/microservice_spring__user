package com.ludo.ms_user.service_implementation.administration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.dto.admin_api.AdminTokenTypeDto;
import com.ludo.ms_user.dto.admin_api.CreateTokenTypeRequestDto;
import com.ludo.ms_user.dto.admin_api.UpdateTokenTypeRequestDto;

import com.ludo.ms_user.entity.TokenType;

import com.ludo.ms_user.exception.global.DataCantBeDeletedException;

import com.ludo.ms_user.service_interface.TokenTypeServiceInt;
import com.ludo.ms_user.service_interface.administration.TokenTypeAdminServiceInt;

import com.ludo.ms_user.utils.AdminDtoBuilder;
import com.ludo.ms_user.utils.TokenTypeUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenTypeAdminServiceImp implements TokenTypeAdminServiceInt {
	
	private final TokenTypeServiceInt tokenTypeService;
	
	private final TokenTypeUtils tokenTypeUtils; 
	private final AdminDtoBuilder adminDtoBuilder;
	
	@Override
	public AdminTokenTypeDto createTokenType(CreateTokenTypeRequestDto body) {
		tokenTypeService.getTokenTypeByName(body.getName(), false);
		TokenType tokenType = tokenTypeService.postTokenType(TokenType.builder()
			.name(body.getName().toUpperCase())
			.description(body.getDescription())
			.tokenExpirationTime(body.getTokenExpirationTime())
			.build()
		);
		
		return adminDtoBuilder.formatAdminTokenTypeDto(tokenType);
	}

	@Override
	public AdminTokenTypeDto getTokenTypeByName(String name) {
		TokenType tokenType = tokenTypeService.getTokenTypeByName(name, true);
		return adminDtoBuilder.formatAdminTokenTypeDto(tokenType);	
	}

	@Override
	public List<AdminTokenTypeDto> getAllTokenType() {
		List<TokenType> tokenTypeList = tokenTypeService.getAllTokenType();
		List<AdminTokenTypeDto> tokenTypeDtoList = new ArrayList<>();
		for (TokenType type : tokenTypeList) {
			tokenTypeDtoList.add(adminDtoBuilder.formatAdminTokenTypeDto(type));
		}
		return tokenTypeDtoList;
	}

	@Override
	public AdminTokenTypeDto updateTokenType(String name, UpdateTokenTypeRequestDto body) {
		TokenType tokenType = tokenTypeService.getTokenTypeByName(name, true);
		tokenType = tokenTypeUtils.checkUpdate(tokenType, body);
		
		TokenType updatedTokenType = tokenTypeService.updateTokenType(tokenType);
		
		return adminDtoBuilder.formatAdminTokenTypeDto(updatedTokenType);
	}

	@Override
	public String updateCanDeleteTokenType(String name) {
		TokenType tokenType = tokenTypeService.getTokenTypeByName(name, true);
		tokenType.setCanBeDeleted(!tokenType.isCanBeDeleted());
		TokenType updatedTokenType = tokenTypeService.updateTokenType(tokenType);
		
		return updatedTokenType.isCanBeDeleted() ? 
				"TokenType : " + name + " now can be deleted" : "TokenType : " + name + " now can't be deleted";
	}
	
	@Override
	public String deleteTokenType(String name) {
		TokenType tokenType = tokenTypeService.getTokenTypeByName(name, true);
		if (tokenType.isCanBeDeleted() == false) { throw new DataCantBeDeletedException(); }
		return tokenTypeService.deleteTokenType(tokenType);
	}



}
