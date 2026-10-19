package com.ludo.ms_user.service_implementation.administration;

import java.security.InvalidParameterException;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.dto.admin_api.AdminSessionTokenDto;

import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.UserAccount;

import com.ludo.ms_user.exception.session_token.TokenAlreadyRevokedException;
import com.ludo.ms_user.exception.session_token.TokenSessionNotFoundException;

import com.ludo.ms_user.exception.user_account.UserAlreadyEnabledException;
import com.ludo.ms_user.exception.user_account.UserBannedException;

import com.ludo.ms_user.service_interface.SessionTokenServiceInt;
import com.ludo.ms_user.service_interface.TokenTypeServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;
import com.ludo.ms_user.service_interface.administration.SessionTokenAdminServiceInt;
import com.ludo.ms_user.service_interface.utilitaire.EmailServiceInt;

import com.ludo.ms_user.utils.AdminDtoBuilder;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionTokenAdminImp implements SessionTokenAdminServiceInt {
	
	private final UserAccountServiceInt userAccountService;
	private final TokenTypeServiceInt tokenTypeService;
	private final SessionTokenServiceInt sessionTokenService;
	private final EmailServiceInt emailService;
	
	private final AdminDtoBuilder adminDtoBuilder;
	
	@Override
	@Transactional
	public List<AdminSessionTokenDto> getSessionTokenByUsername(String username) {
		userAccountService.getUserAccountByUsername(username, true);
		
		List<SessionToken> sessionTokens = sessionTokenService.findAllSessionTokenByUsername(username); 
		List<AdminSessionTokenDto> AdminSessionTokenDtos = new ArrayList<>();
		
		for (SessionToken sessionToken : sessionTokens) {
			AdminSessionTokenDtos.add(adminDtoBuilder.formatAdminSessionTokenDto(sessionToken));
		}
		
		return AdminSessionTokenDtos;
	}

	@Override
	@Transactional
	public List<AdminSessionTokenDto> getSessionTokenByTokenType(String tokenType) {
		tokenTypeService.getTokenTypeByName(tokenType, true);
		
		List<SessionToken> sessionTokens = sessionTokenService.findAllSessionTokenByTokenType(tokenType); 
		List<AdminSessionTokenDto> AdminSessionTokenDtos = new ArrayList<>();
		
		for (SessionToken sessionToken : sessionTokens) {
			AdminSessionTokenDtos.add(adminDtoBuilder.formatAdminSessionTokenDto(sessionToken));
		}
		
		return AdminSessionTokenDtos;
	}

	@Override
	@Transactional
	public List<AdminSessionTokenDto> getAllSessionToken() {
		List<SessionToken> sessionTokens = sessionTokenService.findAllSessionToken(); 
		List<AdminSessionTokenDto> AdminSessionTokenDtos = new ArrayList<>();
		
		for (SessionToken sessionToken : sessionTokens) {
			AdminSessionTokenDtos.add(adminDtoBuilder.formatAdminSessionTokenDto(sessionToken));
		}
		
		return AdminSessionTokenDtos;
	}

	@Override
	@Transactional
	public AdminSessionTokenDto generateAndSendSessionToken(String username, String tokenType) {
		if (tokenType.toUpperCase().equals("REFRESH_TOKEN")) {
			throw new InvalidParameterException("REFRESH_TOKEN can't be generate like this");
		}
		
		tokenTypeService.getTokenTypeByName(tokenType, true);
		UserAccount userAccount = userAccountService.getUserAccountByUsername(username, true);
		
		int tokenLength = 0;
		String emailType = "";
		
		switch (tokenType.toUpperCase()) {
		case "ACCOUNT_ACTIVATION":
			if (userAccount.isEnabled()) {
				throw new UserAlreadyEnabledException();
			} else if (userAccount.isBanned()) {
				throw new UserBannedException();
			}
			tokenLength = 10;
			emailType = "ACTIVATION_MAIL";
			break;
		case "RESET_PASSWORD":
			if (userAccount.isBanned()) {
				throw new UserBannedException();
			}
			tokenLength = 32;
			emailType = "RESET_PASSWORD_MAIL";
			break;
		default:
			throw new TokenSessionNotFoundException(null, tokenType);
		}
		
		String newToken = sessionTokenService.generateRandomSessionToken(tokenLength);
		sessionTokenService.postSessionToken(userAccount, newToken, tokenType);
		emailService.initialiseEmail(emailType, userAccount.getEmail(), newToken);
		
		SessionToken sessionToken = sessionTokenService.findByUserIdAndTokenTypeName(userAccount.getUserId(), tokenType);
		
		return adminDtoBuilder.formatAdminSessionTokenDto(sessionToken);
	}
	
	@Override
	@Transactional
	public AdminSessionTokenDto revokeSessionToken(String tokenId) {
		UUID id = UUID.fromString(tokenId);
		SessionToken sessionToken = sessionTokenService.getSessionTokenById(id);
		if (sessionToken.isRevoked()) {
			throw new TokenAlreadyRevokedException();
		}
		sessionTokenService.revokeSessionToken(sessionToken);
		sessionToken.setRevoked(true);
		return adminDtoBuilder.formatAdminSessionTokenDto(sessionToken);
	}

	@Override
	public String clearSessionToken() {
		List<SessionToken> sessionTokens = sessionTokenService.findAllSessionToken();
		long count = 0l;
		for (SessionToken sessionToken : sessionTokens) {
			if (sessionToken.isConsumed() || sessionToken.isRevoked() 
				|| sessionToken.getExpirationDate().isBefore(Instant.now())) {
				sessionTokenService.deleteSessionToken(sessionToken);
				count++;
			}
		}
		return count + " SessionToken deleted";
	}

}
