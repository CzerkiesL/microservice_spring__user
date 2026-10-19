package com.ludo.ms_user.service_interface.administration;

import java.util.List;

import com.ludo.ms_user.dto.admin_api.AdminTokenTypeDto;
import com.ludo.ms_user.dto.admin_api.CreateTokenTypeRequestDto;
import com.ludo.ms_user.dto.admin_api.UpdateTokenTypeRequestDto;

public interface TokenTypeAdminServiceInt {
	
	AdminTokenTypeDto createTokenType(CreateTokenTypeRequestDto body);
	
	AdminTokenTypeDto getTokenTypeByName(String name);
	
	List<AdminTokenTypeDto> getAllTokenType();
	
	AdminTokenTypeDto updateTokenType(String name, UpdateTokenTypeRequestDto body);
	
	String updateCanDeleteTokenType(String name);
	
	String deleteTokenType(String name);


}
