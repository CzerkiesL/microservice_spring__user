package com.ludo.ms_user.service_implementation;

import com.ludo.ms_user.service_interface.UserAccountServiceInt;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.UserAccount;

import com.ludo.ms_user.repository.UserAccountRepository;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;
import com.ludo.ms_user.exception.user_account.UserNotFoundExeception;
import com.ludo.ms_user.exception.user_account.UsernameOrEmailAlreadyExistException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImp implements UserAccountServiceInt {
	
	private final UserAccountRepository userAccountRepository;
	
	@Override
	public UserAccount getUserAccountById(UUID userId) {
		
		Optional<UserAccount> user = userAccountRepository.findById(userId);
		
		if (user.isEmpty()) { throw new UserNotFoundExeception(); }
		
		return user.get();
	}
	
	@Override
	public UserAccount getUserAccountByEmail(String email, boolean get) {
		
		Optional<UserAccount> user = userAccountRepository.findByEmail(email);

		if (get) {
			if (user.isEmpty()) { throw new UserNotFoundExeception(); }				
			return user.get();			
		} else {
			if (user.isPresent()) { throw new UsernameOrEmailAlreadyExistException(); }				
		}
		
		return null;
	}
	
	@Override
	public UserAccount getUserAccountByUsername(String username, boolean get) {
		
		Optional<UserAccount> user = userAccountRepository.findByUsername(username);
		
		if (get) {
			if (user.isEmpty()) { throw new UserNotFoundExeception(); }				
			return user.get();			
		} else {
			if (user.isPresent()) { throw new UsernameOrEmailAlreadyExistException(); }				
		}
		
		return null;
	}
	
	@Override
	public List<UserAccount> getAllUserAccount() {
		try {
			return userAccountRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of UserAccount : " + e.getMessage());			// TODO: handle exception
		}
	}
	
	@Override
	public List<UserAccount> getUserAccountByRoleContaining(Role role) {
		try {
			return userAccountRepository.findByRolesContaining(role).get();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of UserAccount : " + e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public UserAccount postUserAccount(UserAccount userAccount) {
		try {
			return userAccountRepository.save(userAccount);
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of UserAccount : " + e.getMessage());
		}		
	}
	
	@Override
	public UserAccount updateUserAccount(UserAccount user) {
		
		UserAccount userAccount;
		
		try {
			userAccount = userAccountRepository.save(user);
		} catch (Exception e) {
			throw new UpdateFailedException("Error during update of UserAccount : " + e.getMessage());
		}
		
		return userAccount;
	}
	

	@Override
	public void deleteUserAccount(UUID userId) {
		try {
			userAccountRepository.deleteById(userId);			
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete of UserAccount : " + e.getMessage());
		}
	}
}
