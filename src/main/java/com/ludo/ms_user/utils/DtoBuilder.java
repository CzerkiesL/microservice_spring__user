package com.ludo.ms_user.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ludo.ms_user.dto.PermissionDto;
import com.ludo.ms_user.dto.RoleDto;
import com.ludo.ms_user.dto.TokenTypeDto;
import com.ludo.ms_user.dto.UserAccountDto;
import com.ludo.ms_user.dto.UserProfileDto;
import com.ludo.ms_user.dto.auth_api.LoginUserAccountDto;
import com.ludo.ms_user.dto.user_api.UserResponseDto;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.TokenType;
import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.service_interface.utilitaire.MinioStorageServiceInt;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DtoBuilder {
	
	private final MinioStorageServiceInt minioStorageService;
	
	public PermissionDto formatPermissionDto(Permission permission) {
		return PermissionDto.builder()
			.code(permission.getCode())
			.description(permission.getDescription())
			.build();
	}
	
	public RoleDto formatRoleDto(Role role) {
		List<String> permissionList = new ArrayList<>();
		
		for (Permission permission : role.getPermissions()) {
			permissionList.add(permission.getCode());
		}
		
		return RoleDto.builder()
			.name(role.getName())
			.description(role.getDescription())
			.permissions(permissionList)
			.build();
	}
	
	public TokenTypeDto formatTokenTypeDto(TokenType tokenType) {
		return TokenTypeDto.builder()
				.name(tokenType.getName())
				.description(tokenType.getDescription())
				.tokenExpirationTime(tokenType.getTokenExpirationTime())
				.build();
	}
	
	public UserProfileDto formatUserProfileDto(UserProfile userProfile) {
		return UserProfileDto.builder()
			.profileImageKey(minioStorageService.getPresignedUrl(userProfile.getProfileImageKey()))
			.firstname(userProfile.getFirstname())
			.lastname(userProfile.getLastname())
			.dateOfBirth(userProfile.getDateOfBirth())
			.address(userProfile.getAddress())
			.city(userProfile.getCity())
			.country(userProfile.getCountry())
			.phoneNumber(userProfile.getPhoneNumber())
			.build();
	}
	
	public UserAccountDto formarUserAccountDto(UserAccount userAccount) {
		return UserAccountDto.builder()
			.userId(userAccount.getUserId())
			.username(userAccount.getUsername())
			.email(userAccount.getEmail())
			.build();
	}
	
	public LoginUserAccountDto formatLoginUserAccountDto(UserAccount userAccount, UserProfile userProfile) {
		List<RoleDto> roles = new ArrayList<>();
		
		for (Role role : userAccount.getRoles()) {
			roles.add(formatRoleDto(role));
		}
		UserAccountDto account = formarUserAccountDto(userAccount);
		UserProfileDto profile = formatUserProfileDto(userProfile);
		
		
		return LoginUserAccountDto.builder()
			.userAccount(account)
			.roles(roles)
			.userProfile(profile)
			.build();
	}
	
	public UserResponseDto formatUserResponseDto(UserAccount userAccount, UserProfile userProfile) {
		return UserResponseDto.builder()
			.account(formarUserAccountDto(userAccount))
			.profile(formatUserProfileDto(userProfile))
			.build();
	}
}
