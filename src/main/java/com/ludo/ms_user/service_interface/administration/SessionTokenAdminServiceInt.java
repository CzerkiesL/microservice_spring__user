package com.ludo.ms_user.service_interface.administration;

import java.util.List;

import com.ludo.ms_user.dto.admin_api.AdminSessionTokenDto;

public interface SessionTokenAdminServiceInt {

	List<AdminSessionTokenDto> getSessionTokenByUsername(String username);

	List<AdminSessionTokenDto> getSessionTokenByTokenType(String tokenType);

	List<AdminSessionTokenDto> getAllSessionToken();

	AdminSessionTokenDto generateAndSendSessionToken(String username, String tokenType);

	AdminSessionTokenDto revokeSessionToken(String tokenId);

	String clearSessionToken();
}
