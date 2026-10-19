package com.ludo.ms_user.service_implementation.administration;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.dto.admin_api.AdminUserDto;

import com.ludo.ms_user.entity.BannedUser;
import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.exception.role.RoleAlreadyAssignException;
import com.ludo.ms_user.exception.role.RoleNotAssignException;

import com.ludo.ms_user.service_interface.BannedUserServiceInt;
import com.ludo.ms_user.service_interface.RoleServiceInt;
import com.ludo.ms_user.service_interface.SessionTokenServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;
import com.ludo.ms_user.service_interface.UserProfileServiceInt;

import com.ludo.ms_user.service_interface.administration.UserAdminServiceInt;

import com.ludo.ms_user.utils.AdminDtoBuilder;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImp implements UserAdminServiceInt {
	
	private final UserAccountServiceInt userAccountService;
	private final UserProfileServiceInt userProfileService;
	private final SessionTokenServiceInt sessionTokenService;
	private final BannedUserServiceInt bannedUserService;
	private final RoleServiceInt roleService;
	
	private final AdminDtoBuilder adminDtoBuilder;
	
	@Override
	@Transactional
	public AdminUserDto getUserByUsername(String username) {
		UserAccount userAccount = userAccountService.getUserAccountByUsername(username, true);
		UserProfile userProfile = null;
		if (userAccount.isEnabled()) {
			userProfile = userProfileService.getUserProfileById(userAccount.getUserId(), true);			
		}
		return adminDtoBuilder.formatAdminUserDto(userAccount, userProfile);
	}

	@Override
	@Transactional
	public List<AdminUserDto> getAllUser() {
		List<UserAccount> userAccounts = userAccountService.getAllUserAccount();
		
		List<AdminUserDto> adminUserDtos = new ArrayList<>();
		
		for (UserAccount userAccount : userAccounts) {
			UserProfile userProfile = null;
			if (userAccount.isEnabled()) {
				userProfile = userProfileService.getUserProfileById(userAccount.getUserId(), true);
			}
			adminUserDtos.add(adminDtoBuilder.formatAdminUserDto(userAccount, userProfile));
		}
		
		return adminUserDtos;
	}

	@Override
	@Transactional
	public AdminUserDto banUser(String username, String duration) {
		
		long durationNum;
		try {
		    durationNum = Long.parseLong(duration.trim());
		} catch (NumberFormatException e) {
		    throw new IllegalArgumentException("duration invalide : " + duration + " must be only digit string");
		}
		
		UserAccount userAccount = userAccountService.getUserAccountByUsername(username, true);
		UserProfile userProfile = null;
		if (userAccount.isEnabled()) {
			userProfile = userProfileService.getUserProfileById(userAccount.getUserId(), true);			
		}
		List<SessionToken> sessionTokens = sessionTokenService.findAllSessionTokenByUsername(username);
		
		for (SessionToken sessionToken : sessionTokens) {
			sessionTokenService.revokeSessionToken(sessionToken);
		}
		
		userAccount.setBanned(true);
		userAccount = userAccountService.updateUserAccount(userAccount);
		
		BannedUser bannedUser = BannedUser.builder()
			.banEndDate(Instant.now().plusMillis(durationNum))
			.banEmail(userAccount.getEmail())
			.build();
		
		bannedUserService.postBannedUser(bannedUser);
		
		return adminDtoBuilder.formatAdminUserDto(userAccount, userProfile);
	}

	@Override
	@Transactional
	public AdminUserDto unbanUser(String username) {
		UserAccount userAccount = userAccountService.getUserAccountByUsername(username, true);
		UserProfile userProfile = null;
		if (userAccount.isEnabled()) {
			userProfile = userProfileService.getUserProfileById(userAccount.getUserId(), true);			
		}
		
		userAccount.setBanned(false);
		userAccount = userAccountService.updateUserAccount(userAccount);
		
		BannedUser bannedUser = bannedUserService.getBannedUserByEmail(userAccount.getEmail(), true);
		
		bannedUserService.deleteBannedUser(bannedUser);
		
		return adminDtoBuilder.formatAdminUserDto(userAccount, userProfile);
	}

	@Override
	@Transactional
	public AdminUserDto addRoleUser(String username, String role) {
		UserAccount userAccount = userAccountService.getUserAccountByUsername(username, true);
		UserProfile userProfile = null;
		if (userAccount.isEnabled()) {
			userProfile = userProfileService.getUserProfileById(userAccount.getUserId(), true);			
		}
		Role savedRole = roleService.getRoleByname(role, true);
		
		if (userAccount.getRoles().contains(savedRole)) {
			throw new RoleAlreadyAssignException();		
		}
		
		userAccount.getRoles().add(savedRole);
		
		return adminDtoBuilder.formatAdminUserDto(userAccount, userProfile);		
	}

	@Override
	@Transactional
	public AdminUserDto removeRoleUser(String username, String role) {
		UserAccount userAccount = userAccountService.getUserAccountByUsername(username, true);
		UserProfile userProfile = null;
		if (userAccount.isEnabled()) {
			userProfile = userProfileService.getUserProfileById(userAccount.getUserId(), true);			
		}
		Role savedRole = roleService.getRoleByname(role, true);
		
		if (!userAccount.getRoles().contains(savedRole)) {
			throw new RoleNotAssignException();		
		}
		
		userAccount.getRoles().remove(savedRole);
		
		return adminDtoBuilder.formatAdminUserDto(userAccount, userProfile);
	}

}
