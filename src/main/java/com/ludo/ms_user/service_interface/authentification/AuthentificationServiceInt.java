package com.ludo.ms_user.service_interface.authentification;

import com.ludo.ms_user.dto.auth_api.AskResetPasswordRequestDto;
import com.ludo.ms_user.dto.auth_api.ConfirmAccountRequestDto;
import com.ludo.ms_user.dto.auth_api.LoginRequestDto;
import com.ludo.ms_user.dto.auth_api.LoginResponseDto;
import com.ludo.ms_user.dto.auth_api.RegisterRequestDto;
import com.ludo.ms_user.dto.auth_api.ResendConfirmTokenRequestDto;
import com.ludo.ms_user.dto.auth_api.ResetPasswordRequestDto;

import com.ludo.ms_user.utils.JwtPairs;

public interface AuthentificationServiceInt {
	
	String register(RegisterRequestDto body);
	
	String confirmAccount(ConfirmAccountRequestDto body);
	
	String resendConfirmToken(ResendConfirmTokenRequestDto body);
	
	LoginResponseDto login(LoginRequestDto body);
	
	JwtPairs refreshSession(String refreshToken);

	String askResetPassword(AskResetPasswordRequestDto body);

	String resetPassword(ResetPasswordRequestDto body, String resetPasswordToken);

	String logout(String userId);
}
