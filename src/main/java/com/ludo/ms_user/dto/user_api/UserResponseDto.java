package com.ludo.ms_user.dto.user_api;

import com.ludo.ms_user.dto.UserAccountDto;
import com.ludo.ms_user.dto.UserProfileDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private UserAccountDto account;
	private UserProfileDto profile;
}
