package com.ludo.ms_user.service_implementation.user;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ludo.ms_user.dto.user_api.UpdateAccountRequestDto;
import com.ludo.ms_user.dto.user_api.UserResponseDto;

import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.service_implementation.utilitaire.ImageProcessingServiceImp;

import com.ludo.ms_user.service_interface.SessionTokenServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;
import com.ludo.ms_user.service_interface.UserProfileServiceInt;

import com.ludo.ms_user.service_interface.user.UserServiceInt;

import com.ludo.ms_user.service_interface.utilitaire.ImageProcessingServiceInt;
import com.ludo.ms_user.service_interface.utilitaire.MinioStorageServiceInt;

import com.ludo.ms_user.utils.DtoBuilder;
import com.ludo.ms_user.utils.UserUtils;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserServiceInt {
	
	private final UserAccountServiceInt userAccountService;
	private final UserProfileServiceInt userProfileService;
	private final SessionTokenServiceInt sessionTokenService;
	private final MinioStorageServiceInt minioStorageService;
	private final ImageProcessingServiceInt imageProcessingService;
	
	private final DtoBuilder dtoBuilder;
	private final UserUtils userUtils;
	
	@Override
	@Transactional
	public UserResponseDto getAccount(String userId) {
		UUID id = UUID.fromString(userId);
		UserAccount userAccount = userAccountService.getUserAccountById(id);
		UserProfile userProfile = userProfileService.getUserProfileById(id, true);
		return dtoBuilder.formatUserResponseDto(userAccount, userProfile);
	}

	@Override
	@Transactional
	public UserResponseDto updateAccount(String userId, UpdateAccountRequestDto body) {
		UUID id = UUID.fromString(userId);
		UserAccount userAccount = userAccountService.getUserAccountById(id);
		UserProfile userProfile = userProfileService.getUserProfileById(id, true);
		
		UserAccount updatedAccount = userUtils.checkUserAccountUpdate(userAccount, body);
		UserProfile updatedProfile = userUtils.checkUserProfileUpdate(userProfile, body);
		
		userAccount = userAccountService.updateUserAccount(updatedAccount);
		userProfile = userProfileService.UpdateUserProfile(updatedProfile);
		
		return dtoBuilder.formatUserResponseDto(userAccount, userProfile);
	}

	@Override
	@Transactional
	public UserResponseDto updateImageAccount(String userId, MultipartFile file) {
		UUID id = UUID.fromString(userId);
		UserAccount userAccount = userAccountService.getUserAccountById(id);
		UserProfile userProfile = userProfileService.getUserProfileById(id, true);

		userUtils.validateImage(file);
		
		ImageProcessingServiceImp.ProcessedImage processedImage = imageProcessingService.resizeIfNeeded(file);
		
		String newProfileImageKey = minioStorageService.uploadFile(
			processedImage.inputStream(), processedImage.size(), processedImage.contentType()
			, "profile-avatars", id, "-avatar"
		);
		
		userProfile.setProfileImageKey(newProfileImageKey);
		userProfile = userProfileService.UpdateUserProfile(userProfile);
		
		return dtoBuilder.formatUserResponseDto(userAccount, userProfile);
	}
	
	@Override
	@Transactional
	public String deleteAccount(String userId) {
		UUID id = UUID.fromString(userId);
		UserAccount userAccount = userAccountService.getUserAccountById(id);
		if (userAccount.isBanned()) {
			return "account can't be delete";			
		}
		
		UserProfile userProfile = userProfileService.getUserProfileById(id, true);
		String profileImg = userProfile.getProfileImageKey();
		
		if (!profileImg.equals("defaults/avatar.jpg")) {
			minioStorageService.deleteFile(profileImg);
		}
		
		sessionTokenService.deleteAllTokenByUserId(id);
		userProfileService.deleteUserProfile(userProfile);
		userAccountService.deleteUserAccount(id);
		
		return "account delete with success";
	}


}
