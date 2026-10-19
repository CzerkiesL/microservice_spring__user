package com.ludo.ms_user.dto.auth_api;

import java.util.List;

import com.ludo.ms_user.dto.RoleDto;
import com.ludo.ms_user.dto.UserAccountDto;
import com.ludo.ms_user.dto.UserProfileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserAccountDto {
	private UserAccountDto userAccount;
	private List<RoleDto> roles;
	private UserProfileDto userProfile;
}
