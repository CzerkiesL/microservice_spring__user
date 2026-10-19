package com.ludo.ms_user.scheduling;

import java.time.Instant;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ludo.ms_user.entity.BannedUser;
import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.service_interface.BannedUserServiceInt;
import com.ludo.ms_user.service_interface.SessionTokenServiceInt;
import com.ludo.ms_user.service_interface.UserAccountServiceInt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaintenanceCleanup {

	private final BannedUserServiceInt bannedUserService;
	private final UserAccountServiceInt userAccountService;
	private final SessionTokenServiceInt sessionTokenService;
	
	@Scheduled(cron = "0 0 8 * * *", zone = "Europe/Paris")
	public void cleanupExpiredBannedUsers() {
		log.info("cleanup expired BannedUser start");
		
		List<BannedUser> expiredBannedUsers =  bannedUserService.getAllExpiredBannedUser(Instant.now());
		
		if (expiredBannedUsers.size() <= 0) {
			log.info("no expired BannedUser found");
			log.info("cleanup expired BannedUser end");		
			return;
		}
		
		log.info("{} expired BannedUser found", expiredBannedUsers.size());
		
		long counter = 0;
		
		for (BannedUser expiredBan : expiredBannedUsers) {
			bannedUserService.deleteBannedUser(expiredBan);
			
			UserAccount user = userAccountService.getUserAccountByEmail(expiredBan.getBanEmail(), true);
			user.setBanned(false);
			userAccountService.updateUserAccount(user);
			
			counter++;
		}
		
		log.info("{} expired BannedUser removed", counter);
		log.info("cleanup expired BannedUser end");		
	}
	
	@Scheduled(cron = "0 0 8 * * 1", zone = "Europe/Paris")
	public void cleanupExpiredSessionTokens() {
		log.info("cleanup expired SessionToken start");
		List<SessionToken> expiredSessionTokens = sessionTokenService.getAllExpiredSessionToken(Instant.now());
		
		if (expiredSessionTokens.size() <= 0) {
			log.info("no expired expiredToken found");
			log.info("cleanup expired SessionToken end");
			return;
		}

		log.info("{} expired expiredToken found", expiredSessionTokens.size());
		
		long counter = 0;
		
		for (SessionToken expiredToken : expiredSessionTokens) {
			sessionTokenService.deleteSessionToken(expiredToken);
			counter++;
		}
		
		log.info("{} expired expiredToken removed", counter);
		log.info("cleanup expired SessionToken end");
	}
}
