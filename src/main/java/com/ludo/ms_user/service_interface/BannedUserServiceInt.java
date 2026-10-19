package com.ludo.ms_user.service_interface;

import java.time.Instant;

import java.util.List;
import java.util.UUID;

import com.ludo.ms_user.entity.BannedUser;

public interface BannedUserServiceInt {
	
	List<BannedUser> getAllExpiredBannedUser(Instant now);
	
	BannedUser getBannedUserById(UUID banId);
	
	BannedUser getBannedUserByEmail(String email, boolean get);
	
	List<BannedUser> getAllBannedUser();
	
	BannedUser postBannedUser(BannedUser bannedUser);
	
	BannedUser updateBannedUser(BannedUser bannedUser);
	
	void deleteBannedUser(BannedUser bannedUser);
}
