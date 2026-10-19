package com.ludo.ms_user.service_interface;

import java.time.Instant;

import java.util.List;
import java.util.UUID;

import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.UserAccount;

public interface SessionTokenServiceInt {
	
	List<SessionToken> getAllExpiredSessionToken(Instant now);
	
	String generateRandomSessionToken(int number);	
	
	void checkToken(String rawToken, SessionToken token);
	
	public SessionToken getSessionTokenByTokenHashAndType(String token, String tokenType);
	
	SessionToken findByUserIdAndTokenTypeName(UUID userId, String tokenTypeName);
	
	SessionToken getSessionTokenById(UUID tokenId);
	
	List<SessionToken> findAllSessionTokenByUsername(String username); 

	List<SessionToken> findAllSessionTokenByTokenType(String tokenType); 

	List<SessionToken> findAllSessionToken(); 
	
	void postSessionToken(UserAccount user, String tokenString, String tokenType);
	
	void consumeSessionToken(SessionToken sessionToken);
	
	void revokeSessionToken(SessionToken sessionToken);
	
	void deleteAllTokenByUserId(UUID userId);

	void deleteSessionToken(SessionToken sessionToken);
}
