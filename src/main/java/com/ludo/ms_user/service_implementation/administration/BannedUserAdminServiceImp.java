package com.ludo.ms_user.service_implementation.administration;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.dto.admin_api.AdminBannedUserDto;

import com.ludo.ms_user.entity.BannedUser;
import com.ludo.ms_user.entity.UserAccount;

import com.ludo.ms_user.exception.user_account.UserNotFoundExeception;

import com.ludo.ms_user.service_interface.BannedUserServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;
import com.ludo.ms_user.service_interface.administration.BannedUserAdminServiceInt;

import com.ludo.ms_user.utils.AdminDtoBuilder;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannedUserAdminServiceImp implements BannedUserAdminServiceInt {
	
	private final UserAccountServiceInt userAccountService;
	private final BannedUserServiceInt bannedUserService;
	
	private final AdminDtoBuilder adminDtoBuilder;
	
	@Override
	@Transactional
	public AdminBannedUserDto getBannedUserByBanId(String banId) {
		UUID id = UUID.fromString(banId);
		BannedUser bannedUser = bannedUserService.getBannedUserById(id);
		return adminDtoBuilder.formatAdminBannedUserDto(bannedUser);
	}

	@Override
	@Transactional
	public List<AdminBannedUserDto> getAllBannedUser() {
		List<BannedUser> bannedUsers = bannedUserService.getAllBannedUser();
		List<AdminBannedUserDto> adminBannedUserDtos = new ArrayList<>();
		for (BannedUser bannedUser : bannedUsers) {
			adminBannedUserDtos.add(adminDtoBuilder.formatAdminBannedUserDto(bannedUser));
		}
		return adminBannedUserDtos;
	}

	@Override
	@Transactional
	public AdminBannedUserDto updateBannedUserDuration(String banId, String duration) {
		long durationNum;
		try {
		    durationNum = Long.parseLong(duration.trim());
		} catch (NumberFormatException e) {
		    throw new IllegalArgumentException("duration invalide : " + duration + " must be only digit string");
		}
		UUID id = UUID.fromString(banId);
		BannedUser bannedUser = bannedUserService.getBannedUserById(id);
		
		bannedUser.setBanEndDate(bannedUser.getCreatedAt().plusMillis(durationNum));
		bannedUser = bannedUserService.updateBannedUser(bannedUser);
		return adminDtoBuilder.formatAdminBannedUserDto(bannedUser);
	}

	@Override
	@Transactional
	public String clearBannedUser() {
		List<BannedUser> bannedUsers = bannedUserService.getAllBannedUser();
		long count = 0;
		for (BannedUser bannedUser : bannedUsers) {
			if (bannedUser.getBanEndDate().isBefore(Instant.now())) {
				bannedUserService.deleteBannedUser(bannedUser);
				UserAccount userAccount = null;
				boolean found;
				
				try {
					userAccount = userAccountService.getUserAccountByEmail(bannedUser.getBanEmail(), true);					
					found = true;
				} catch (UserNotFoundExeception ignored) {
					found = false;
				}
				
				if (found) {
					userAccount.setBanned(false);
					userAccountService.updateUserAccount(userAccount);
				}
				
				count++;
			}
		}
		return count + " BannedUser was end";
	}

}
