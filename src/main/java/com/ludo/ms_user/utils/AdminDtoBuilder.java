package com.ludo.ms_user.utils;

import java.time.Instant;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ludo.ms_user.dto.admin_api.AdminBannedUserDto;
import com.ludo.ms_user.dto.admin_api.AdminPermissionDto;
import com.ludo.ms_user.dto.admin_api.AdminRoleDto;
import com.ludo.ms_user.dto.admin_api.AdminTokenTypeDto;
import com.ludo.ms_user.dto.admin_api.AdminUserDto;
import com.ludo.ms_user.dto.admin_api.AdminSessionTokenDto;

import com.ludo.ms_user.entity.BannedUser;
import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.TokenType;
import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.service_interface.utilitaire.MinioStorageServiceInt;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminDtoBuilder {
	
	private final MinioStorageServiceInt minioStorageService;
	
	public AdminUserDto formatAdminUserDto(UserAccount userAccount, UserProfile userProfile) {
		
		String url = null;
		if (userProfile != null) {
			url = minioStorageService.getPresignedUrl(userProfile.getProfileImageKey());
		}
		
		List<String> roles = userAccount.getRoles().stream()
			.map(r -> r.getName())
			.toList();
		
		Instant lastUpdate = null;
		if (userProfile == null) {
			lastUpdate = userAccount.getUpdatedAt();
		} else if (userAccount.getUpdatedAt().isAfter(userProfile.getUpdatedAt())) {
			lastUpdate = userAccount.getUpdatedAt();
		} else {
			lastUpdate = userProfile.getUpdatedAt();
		}
		
		return AdminUserDto.builder()
			.userId(userAccount.getUserId())
			.Username(userAccount.getUsername())
			.email(userAccount.getEmail())
			.firstname(userProfile != null ? userProfile.getFirstname() : null)
			.lastname(userProfile != null ? userProfile.getLastname() : null)
			.country(userProfile != null ? userProfile.getCountry() : null)
			.city(userProfile != null ? userProfile.getCity() : null)
			.profileImageUrl(url)
			.roles(roles)
			.createdAt(userAccount.getCreatedAt())
			.updatedAt(lastUpdate)
			.banned(userAccount.isBanned())
			.enabled(userAccount.isEnabled())
			.build();
	}
	
	public AdminRoleDto formatAdminRoleDto(Role role) {
		
		List<String> permissions = role.getPermissions().stream()
			.map(p -> p.getCode())
			.toList();
		
		return AdminRoleDto.builder()
			.roleId(role.getRoleId())
			.name(role.getName())
			.description(role.getDescription())
			.permissions(permissions)
			.createdAt(role.getCreatedAt())
			.updatedAt(role.getUpdatedAt())
			.canBeDeleted(role.isCanBeDeleted())
			.build();
	}
	
	public AdminPermissionDto formatAdminPermissionDto(Permission permission ) {
		return AdminPermissionDto.builder()
			.permissionId(permission.getPermissionId())
			.code(permission.getCode())
			.description(permission.getDescription())
			.createdAt(permission.getCreatedAt())
			.updatedAt(permission.getUpdatedAt())
			.canBeDeleted(permission.isCanBeDeleted())
			.build();
	}
	
	public AdminTokenTypeDto formatAdminTokenTypeDto(TokenType tokenType) {
		
		long seconds = tokenType.getTokenExpirationTime() / 1000;
		long minutes = seconds / 60;
		seconds %= 60;
		long hours = minutes / 60;
		minutes %= 60;
		long days = hours / 24;
		hours %= 24;
		
		String expirationTime = "";
		
		String secondStr = seconds >= 10 ? seconds + "s" : "0" + seconds + "s";
		String minuteStr = minutes >= 10 ? minutes + "m" : "0" + minutes + "m";
		String hourStr = hours >= 10 ? hours + "h" : "0" + hours + "h";
		String dayStr = days >= 10 ? days + "d" : "0" + days + "d";
		
		if (days > 0) 
			expirationTime += (dayStr + " ");
		if (hours > 0 || days > 0) 
			expirationTime += (hourStr + " ");
		if (minutes > 0 || hours > 0 || days > 0) 
			expirationTime += (minuteStr + " ");
		if (seconds > 0 || minutes > 0 || hours > 0 || days > 0) 
			expirationTime += secondStr;		
		
		return AdminTokenTypeDto.builder()
			.tokenTypeId(tokenType.getTokenTypeId())
			.name(tokenType.getName())
			.description(tokenType.getDescription())
			.tokenExpirationTime(expirationTime)
			.createdAt(tokenType.getCreatedAt())
			.updatedAt(tokenType.getUpdatedAt())
			.canBeDeleted(tokenType.isCanBeDeleted())
			.build();
	}
	
	public AdminSessionTokenDto formatAdminSessionTokenDto(SessionToken sessionToken) {
		return AdminSessionTokenDto.builder()
			.tokenId(sessionToken.getTokenId())
			.expirationDate(sessionToken.getExpirationDate())
			.tokenType(sessionToken.getTokenType().getName())
			.user(sessionToken.getUser().getUsername())
			.consumed(sessionToken.isConsumed())
			.revoked(sessionToken.isRevoked())
			.createdAt(sessionToken.getCreatedAt())
			.updatedAt(sessionToken.getUpdatedAt())
			.build();
	}
	
	public AdminBannedUserDto formatAdminBannedUserDto(BannedUser bannedUser) {
		return AdminBannedUserDto.builder()
			.banId(bannedUser.getBanId())
			.banEndDate(bannedUser.getBanEndDate())
			.banEmail(bannedUser.getBanEmail())
			.createdAt(bannedUser.getCreatedAt())
			.updatedAt(bannedUser.getUpdatedAt())
			.build();
	}
}
