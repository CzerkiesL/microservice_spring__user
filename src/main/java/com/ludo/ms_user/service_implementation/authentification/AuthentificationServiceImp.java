package com.ludo.ms_user.service_implementation.authentification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.config.MsUserConfiguration;

import com.ludo.ms_user.dto.auth_api.AskResetPasswordRequestDto;
import com.ludo.ms_user.dto.auth_api.ConfirmAccountRequestDto;
import com.ludo.ms_user.dto.auth_api.LoginRequestDto;
import com.ludo.ms_user.dto.auth_api.LoginResponseDto;
import com.ludo.ms_user.dto.auth_api.LoginUserAccountDto;
import com.ludo.ms_user.dto.auth_api.RegisterRequestDto;
import com.ludo.ms_user.dto.auth_api.ResendConfirmTokenRequestDto;
import com.ludo.ms_user.dto.auth_api.ResetPasswordRequestDto;

import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.exception.global.InvalidRefreshTokenException;
import com.ludo.ms_user.exception.user_account.UserBannedException;
import com.ludo.ms_user.exception.user_account.UserNotFoundExeception;

import com.ludo.ms_user.service_interface.BannedUserServiceInt;
import com.ludo.ms_user.service_interface.RoleServiceInt;
import com.ludo.ms_user.service_interface.SessionTokenServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;
import com.ludo.ms_user.service_interface.UserProfileServiceInt;

import com.ludo.ms_user.service_interface.authentification.AuthentificationServiceInt;

import com.ludo.ms_user.service_interface.utilitaire.EmailServiceInt;

import com.ludo.ms_user.utils.DtoBuilder;
import com.ludo.ms_user.utils.JwtPairs;
import com.ludo.ms_user.utils.JwtUtils;
import com.ludo.ms_user.utils.UserAccountUtils;

import io.jsonwebtoken.Claims;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthentificationServiceImp implements AuthentificationServiceInt {
	
	private final UserAccountServiceInt userAccountService;
	private final SessionTokenServiceInt sessionTokenService;
	private final RoleServiceInt roleService;
	private final UserProfileServiceInt userProfileService;
	private final BannedUserServiceInt bannedUserService;
	private final EmailServiceInt emailService;
	
	private final BCryptPasswordEncoder bCrypt;
	private final MsUserConfiguration config;
	private final JwtUtils jwtUtils;
	private final UserAccountUtils userAccountUtils;
	private final DtoBuilder dtoBuilder;
	
	@Override
	@Transactional
	public String register(RegisterRequestDto body) {
		
		userAccountService.getUserAccountByEmail(body.getEmail(), false);
		bannedUserService.getBannedUserByEmail(body.getEmail(), false);
		userAccountService.getUserAccountByUsername(body.getUsername(), false);
		userAccountUtils.checkConfirmPassword(body.getPassword(), body.getConfirmPassword());
		
		Role role = roleService.getRoleByname("USER", true);
		
		UserAccount userAccount = UserAccount.builder()
			.username(body.getUsername())
			.email(body.getEmail())
			.passwordHash(bCrypt.encode(body.getPassword()))
			.enabled(config.isUserAccountAutoEnabled() ? true : false)
			.build();
		
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		userAccount.setRoles(roles);
		
		UserAccount user = userAccountService.postUserAccount(userAccount);
		
		if (config.isUserAccountAutoEnabled() == false) {
			String activationToken = sessionTokenService.generateRandomSessionToken(10);
			sessionTokenService.postSessionToken(user, activationToken, "ACCOUNT_ACTIVATION");	
			emailService.initialiseEmail("ACTIVATION_MAIL", user.getEmail(), activationToken);
		}
			
		return "User account create with success";
	}
	
	@Override
	@Transactional
	public String confirmAccount(ConfirmAccountRequestDto body) {
		
		UserAccount user = userAccountService.getUserAccountByUsername(body.getUsername(), true);
		userAccountUtils.checkUserAccount(user, true, true);
		
		SessionToken sessionToken = sessionTokenService.findByUserIdAndTokenTypeName(user.getUserId(), "ACCOUNT_ACTIVATION");
		sessionTokenService.checkToken(body.getToken(), sessionToken);
		
		user.setEnabled(true);
		userAccountService.updateUserAccount(user);
		userProfileService.postUserProfile(UserProfile.builder().user(user).build());		
		
		return "User account activated with success";
	}

	@Override
	@Transactional
	public String resendConfirmToken(ResendConfirmTokenRequestDto body) {
		
		UserAccount user = userAccountService.getUserAccountByEmail(body.getEmail(), true);
		userAccountUtils.checkUserAccount(user, true, true);
		
		String activationToken = sessionTokenService.generateRandomSessionToken(10);
		sessionTokenService.postSessionToken(user, activationToken, "ACCOUNT_ACTIVATION");	
		emailService.initialiseEmail("ACTIVATION_MAIL", user.getEmail(), activationToken);
		
		return "Activation token resend";
	}

	@Override
	@Transactional
	public LoginResponseDto login(LoginRequestDto body) {
		
		UserAccount user = userAccountUtils.checkCredentials(body.getEmail(), body.getUsername(), body.getPassword());
		userAccountUtils.checkUserAccount(user, true, false);
		UserProfile userProfile = userProfileService.getUserProfileById(user.getUserId(), true);
		
		JwtPairs jwtPair = jwtUtils.signToken(user);
		sessionTokenService.postSessionToken(user, jwtPair.refreshToken, "REFRESH_TOKEN");
		
		LoginUserAccountDto loginUserAccountDto = dtoBuilder.formatLoginUserAccountDto(user, userProfile);
		
		return LoginResponseDto.builder()
				.user(loginUserAccountDto)
				.accessToken(jwtPair.getAccessToken())
				.refreshToken(jwtPair.getRefreshToken())
				.build();
	}

	@Override
	@Transactional
	public JwtPairs refreshSession(String refreshToken) {
		
		try {
			Claims claims = jwtUtils.validateRefreshToken(refreshToken);
		
			UUID userId = UUID.fromString(claims.getSubject());
			SessionToken sessionToken = sessionTokenService.findByUserIdAndTokenTypeName(userId, "REFRESH_TOKEN");
	
			sessionTokenService.checkToken(refreshToken, sessionToken);
			
			UserAccount user = userAccountService.getUserAccountById(userId);
			
			JwtPairs jwtPair = jwtUtils.signToken(user);
			sessionTokenService.postSessionToken(user, jwtPair.refreshToken, "REFRESH_TOKEN");
			
			return jwtPair;
		
		} catch (Exception e) {
			throw new InvalidRefreshTokenException();
		}
	}

	@Override
	@Transactional
	public String askResetPassword(AskResetPasswordRequestDto body) {
		try {
			UserAccount user = userAccountService.getUserAccountByEmail(body.getEmail(), true);
			userAccountUtils.checkUserAccount(user, true, false);
			
			String resetPasswordToken = sessionTokenService.generateRandomSessionToken(32);
			sessionTokenService.postSessionToken(user, resetPasswordToken, "RESET_PASSWORD");
			
			emailService.initialiseEmail("RESET_PASSWORD_MAIL", body.getEmail(), resetPasswordToken);
			
		} catch (UserNotFoundExeception | UserBannedException ignored) {}
		
		return "If this email exists, a reset link has been sent";
	}

	@Override
	@Transactional
	public String resetPassword(ResetPasswordRequestDto body, String resetPasswordToken) {
		userAccountUtils.checkConfirmPassword(body.getPassword(), body.getConfirmPassword());
		
		SessionToken sessionToken = sessionTokenService.getSessionTokenByTokenHashAndType(resetPasswordToken, "RESET_PASSWORD");
		
		UserAccount user = userAccountService.getUserAccountById(sessionToken.getUser().getUserId());
		userAccountUtils.checkUserAccount(user, true, false);
		
		sessionTokenService.consumeSessionToken(sessionToken);
		
		user.setPasswordHash(bCrypt.encode(body.getPassword()));
		userAccountService.updateUserAccount(user);
		
		return "your password is reset";
	}

	@Override
	@Transactional
	public String logout(String userId) {
		UUID id = UUID.fromString(userId);
		
		SessionToken sessionToken = sessionTokenService.findByUserIdAndTokenTypeName(id, "REFRESH_TOKEN");
		sessionTokenService.revokeSessionToken(sessionToken);
		
		return "User logout with success";
	}

}
