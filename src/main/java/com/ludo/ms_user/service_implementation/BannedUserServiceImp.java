package com.ludo.ms_user.service_implementation;

import java.time.Instant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.BannedUser;

import com.ludo.ms_user.exception.banned_user.BannedUserAlreadyExistException;
import com.ludo.ms_user.exception.banned_user.BannedUserNotFoundException;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;

import com.ludo.ms_user.repository.BannedUserRepository;

import com.ludo.ms_user.service_interface.BannedUserServiceInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BannedUserServiceImp implements BannedUserServiceInt {
	
	private final BannedUserRepository bannedUserRepository;

	@Override
	public List<BannedUser> getAllExpiredBannedUser(Instant now) {
		try {
			return bannedUserRepository.findExpiredBans(now);
		} catch (Exception e) {
			throw new GetFailedException("Error during get of BannedUser : " + e.getMessage());
		}
	}
	
	@Override
	public BannedUser getBannedUserById(UUID banId) {
		Optional<BannedUser> bannedUser = bannedUserRepository.findById(banId);
		if (bannedUser.isEmpty()) {throw new BannedUserNotFoundException();}
		return bannedUser.get();
	}
	
	@Override
	public BannedUser getBannedUserByEmail(String email, boolean get) {
		Optional<BannedUser> bannedUser = bannedUserRepository.findByBanEmail(email);
		
		if (get) {
			if (bannedUser.isEmpty()) {throw new BannedUserNotFoundException();}
			return bannedUser.get();
		} else {
			if (bannedUser.isPresent()) {throw new BannedUserAlreadyExistException();}			
			return null;
		}
	}

	@Override
	public List<BannedUser> getAllBannedUser() {
		try {
			return bannedUserRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of BannedUser : " + e.getMessage());
		}
	}

	@Override
	public BannedUser postBannedUser(BannedUser bannedUser) {
		try {
			return bannedUserRepository.save(bannedUser);
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of BannedUser : " + e.getMessage());
		}
	}

	@Override
	public BannedUser updateBannedUser(BannedUser bannedUser) {
		try {
			return bannedUserRepository.save(bannedUser);
		} catch (Exception e) {
			throw new UpdateFailedException("Error during update of BannedUser : " + e.getMessage());
		}
	}

	@Override
	public void deleteBannedUser(BannedUser bannedUser) {
		try {
			bannedUserRepository.delete(bannedUser);
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete of BannedUser : " + e.getMessage());
		}
	}


}
