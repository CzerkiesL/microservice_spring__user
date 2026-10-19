package com.ludo.ms_user.service_interface.user;

import org.springframework.web.multipart.MultipartFile;

import com.ludo.ms_user.dto.user_api.UpdateAccountRequestDto;
import com.ludo.ms_user.dto.user_api.UserResponseDto;

public interface UserServiceInt {

	UserResponseDto getAccount(String userId);

	UserResponseDto updateAccount(String userId, UpdateAccountRequestDto body);

	UserResponseDto updateImageAccount(String userId, MultipartFile file);
	
	String deleteAccount(String userId);
}
