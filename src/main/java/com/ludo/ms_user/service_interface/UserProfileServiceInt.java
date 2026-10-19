package com.ludo.ms_user.service_interface;

import java.util.List;
import java.util.UUID;

import com.ludo.ms_user.entity.UserProfile;

public interface UserProfileServiceInt {
	
	UserProfile getUserProfileById(UUID id, boolean get);
	
	List<UserProfile> getAllUserProfile();
	
	UserProfile postUserProfile(UserProfile userProfile);
	
	UserProfile UpdateUserProfile(UserProfile userProfile);
	
	String deleteUserProfile(UserProfile userProfile);
}
