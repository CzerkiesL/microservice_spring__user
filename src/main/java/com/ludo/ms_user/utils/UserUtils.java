package com.ludo.ms_user.utils;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;

import org.springframework.web.multipart.MultipartFile;

import com.ludo.ms_user.dto.user_api.UpdateAccountRequestDto;

import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.exception.user_profile.ProfileImageException;

import com.ludo.ms_user.service_interface.UserAccountServiceInt;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserUtils {
	
	private final UserAccountServiceInt userAccountService;
	
	private final BCryptPasswordEncoder bCrypt;
	private final UserAccountUtils userAccountUtils;
	
	public UserAccount checkUserAccountUpdate(UserAccount userAccount, UpdateAccountRequestDto body) {
		if (StringUtils.hasText(body.getUsername())) {
			if (!body.getUsername().toLowerCase().equals(userAccount.getUsername().toLowerCase())) {
				userAccountService.getUserAccountByUsername(body.getUsername(), false);
				userAccount.setUsername(body.getUsername());
			}
		}
		if (StringUtils.hasText(body.getEmail())) {
			if (!body.getEmail().toLowerCase().equals(userAccount.getEmail().toLowerCase())) {
				userAccountService.getUserAccountByEmail(body.getEmail(), false);
				userAccount.setEmail(body.getEmail());
			}
		}
		if (StringUtils.hasText(body.getPassword()) && StringUtils.hasText(body.getConfirmPassword())) {
			userAccountUtils.checkConfirmPassword(body.getPassword(), body.getConfirmPassword());
			userAccount.setPasswordHash(bCrypt.encode(body.getPassword()));
		}
		
		return userAccount;
	}
	
	public UserProfile checkUserProfileUpdate(UserProfile userProfile, UpdateAccountRequestDto body) {
		if (StringUtils.hasText(body.getFirstname())) {
			if (!body.getFirstname().toLowerCase().equals(userProfile.getFirstname().toLowerCase())) {
				userProfile.setFirstname(body.getFirstname());
			}
		}
		if (StringUtils.hasText(body.getLastname())) {
			if (!body.getLastname().toLowerCase().equals(userProfile.getLastname().toLowerCase())) {
				userProfile.setLastname(body.getLastname());
			}
		}
		if (StringUtils.hasText(body.getDateOfBirth())) {
			LocalDate dateOfBirth = LocalDate.parse(body.getDateOfBirth());
			if (!dateOfBirth.equals(userProfile.getDateOfBirth())) {
				userProfile.setDateOfBirth(dateOfBirth);
			}
		}
		if (StringUtils.hasText(body.getAddress())) {
			if (!body.getAddress().toLowerCase().equals(userProfile.getAddress().toLowerCase())) {
				userProfile.setAddress(body.getAddress());
			}
		}
		if (StringUtils.hasText(body.getCity())) {
			if (!body.getCity().toLowerCase().equals(userProfile.getCity().toLowerCase())) {
				userProfile.setCity(body.getCity());
			}
		}
		if (StringUtils.hasText(body.getCountry())) {
			if (!body.getCountry().toLowerCase().equals(userProfile.getCountry().toLowerCase())) {
				userProfile.setCountry(body.getCountry());
			}
		}
		if (StringUtils.hasText(body.getPhoneNumber())) {
			if (!body.getPhoneNumber().equals(userProfile.getPhoneNumber())) {
				userProfile.setPhoneNumber(body.getPhoneNumber());
			}
		}
		
		return userProfile;
	}
	
    public void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ProfileImageException("Profile image is required");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ProfileImageException("Only image files are allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new ProfileImageException("Profile image must be less than 5 MB");
        }
    }
}
