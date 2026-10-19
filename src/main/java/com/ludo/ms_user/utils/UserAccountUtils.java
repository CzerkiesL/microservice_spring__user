package com.ludo.ms_user.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Component;

import com.ludo.ms_user.entity.UserAccount;

import com.ludo.ms_user.exception.user_account.PasswordNotCorrespondException;
import com.ludo.ms_user.exception.user_account.UserAlreadyEnabledException;
import com.ludo.ms_user.exception.user_account.UserBannedException;
import com.ludo.ms_user.exception.user_account.UserCredentialsException;
import com.ludo.ms_user.exception.user_account.UserNotFoundExeception;

import com.ludo.ms_user.service_interface.UserAccountServiceInt;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAccountUtils {
	
	private final UserAccountServiceInt userAccountService;
	private final BCryptPasswordEncoder bCrypt;
	
	public boolean checkConfirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			throw new PasswordNotCorrespondException();
		}
		return true;
	}
	
	public void checkUserAccount(UserAccount user, boolean checkIsBanned, boolean checkIsEnabled) {
		if (checkIsBanned && user.isBanned()) { throw new UserBannedException(); }
		if (checkIsEnabled && user.isEnabled()) { throw new UserAlreadyEnabledException(); }
	}
	
	public UserAccount checkCredentials(String email, String username, String password) {
		
		UserAccount user;
		
		if (email != null && !email.isBlank()) {
			user = userAccountService.getUserAccountByEmail(email, true);
		} else if (username != null && !username.isBlank() ) {
			user = userAccountService.getUserAccountByUsername(username, true);
		} else { throw new UserCredentialsException("username or email required for login"); }
	
		if (user == null) { throw new UserNotFoundExeception(); }
		if (!bCrypt.matches(password, user.getPasswordHash())) { throw new UserCredentialsException("wrong password"); }
		
		return user;
	}
}
