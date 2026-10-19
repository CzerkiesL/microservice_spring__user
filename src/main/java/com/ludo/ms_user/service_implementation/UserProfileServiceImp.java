package com.ludo.ms_user.service_implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;
import com.ludo.ms_user.exception.user_profile.UserProfileAlreadyExistException;
import com.ludo.ms_user.exception.user_profile.UserProfileNotFoundException;

import com.ludo.ms_user.repository.UserProfileRepository;

import com.ludo.ms_user.service_interface.UserProfileServiceInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImp implements UserProfileServiceInt {
	
	private final UserProfileRepository userProfileRepository;
	
	@Override
	public UserProfile getUserProfileById(UUID id, boolean get) {
		Optional<UserProfile> userProfile = userProfileRepository.findById(id);
		
		if (get) {
			if (userProfile.isEmpty()) { throw new UserProfileNotFoundException();}
			return userProfile.get();
		} else {
			if (userProfile.isPresent()) { throw new UserProfileAlreadyExistException();}
		}
		return null;
	}

	@Override
	public List<UserProfile> getAllUserProfile() {
		try {
			return userProfileRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of UserProfile list : " + e.getMessage());
		}
	}

	@Override
	public UserProfile postUserProfile(UserProfile userProfile) {
		try {
			return userProfileRepository.save(userProfile);
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of UserProfile : " + e.getMessage());
		}
	}

	@Override
	public UserProfile UpdateUserProfile(UserProfile userProfile) {
		try {
			return userProfileRepository.save(userProfile);
		} catch (Exception e) {
			throw new UpdateFailedException("Error during update of UserProfile : " + e.getMessage());
		}
	}

	@Override
	public String deleteUserProfile(UserProfile userProfile) {
		try {			
			userProfileRepository.delete(userProfile);
			return "User profile : " + userProfile.getUser().getUsername() + " delete with success";
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete of UserProfile : " + e.getMessage());
		}
	}

}
