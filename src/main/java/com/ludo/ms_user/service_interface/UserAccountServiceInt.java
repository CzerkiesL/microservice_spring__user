package com.ludo.ms_user.service_interface;

import java.util.List;
import java.util.UUID;

import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.UserAccount;

public interface UserAccountServiceInt {
	
	UserAccount getUserAccountById(UUID userId);
	
	UserAccount getUserAccountByEmail(String email, boolean get);
	
	UserAccount getUserAccountByUsername(String username, boolean get);
	
	List<UserAccount> getAllUserAccount();
	
	List<UserAccount> getUserAccountByRoleContaining(Role role);
	
	UserAccount postUserAccount(UserAccount userAccount);
	
	UserAccount updateUserAccount(UserAccount user);
	
	void deleteUserAccount(UUID userId);

}
