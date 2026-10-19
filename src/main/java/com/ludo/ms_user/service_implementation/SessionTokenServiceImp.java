package com.ludo.ms_user.service_implementation;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.time.Instant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.SessionToken;
import com.ludo.ms_user.entity.TokenType;
import com.ludo.ms_user.entity.UserAccount;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;

import com.ludo.ms_user.exception.session_token.TokenAlreadyConsumedException;
import com.ludo.ms_user.exception.session_token.TokenRevokedException;
import com.ludo.ms_user.exception.session_token.TokenSessionInvalidException;
import com.ludo.ms_user.exception.session_token.TokenSessionNotFoundException;

import com.ludo.ms_user.repository.SessionTokenRepository;

import com.ludo.ms_user.service_interface.SessionTokenServiceInt;
import com.ludo.ms_user.service_interface.TokenTypeServiceInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionTokenServiceImp implements SessionTokenServiceInt {
	
	private final SessionTokenRepository sessionTokenRepository;
	
	private final TokenTypeServiceInt tokenTypeService;
	
	@Override
	public List<SessionToken> getAllExpiredSessionToken(Instant now) {
		try {
			return sessionTokenRepository.findExpiredSessionTokens(now);
		} catch (Exception e) {
			throw new GetFailedException("Error during get of SessionToken : " + e.getMessage());
		}
	}
	
	private String hashToken(String token) {
		
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        
	        for (byte b : hash) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Error hashing token", e);
	    }
	}
	
	@Override
	public String generateRandomSessionToken(int number) {
		String token = "";
		for (int i = 0; i < number; i++) {
			int randNum = (int) (Math.random() * 10);
			token += randNum;
		}
		return token;
	}

	@Override
	public void checkToken(String rawToken, SessionToken token) {
		
		if (token.isRevoked()) { throw new TokenRevokedException(); }
		if (token.isConsumed()) { throw new TokenAlreadyConsumedException(); }
		
		consumeSessionToken(token);
		
		boolean correspond = token.getTokenHash().equals(hashToken(rawToken));
		Instant now = Instant.now();
		boolean notExpired = now.isBefore(token.getExpirationDate());
		
		if (!correspond || !notExpired) {
			throw new TokenSessionInvalidException(token.getTokenType().getName());
		}
	}

	@Override
	public SessionToken getSessionTokenByTokenHashAndType(String token, String tokenType) {
		
		String tokenHash = hashToken(token);
		Optional<SessionToken> sessionToken = sessionTokenRepository.findByTokenHashAndTokenTypeName(tokenHash, tokenType);
		
		if (sessionToken.isEmpty()) { throw new TokenSessionNotFoundException(null, tokenType); }
		
		return sessionToken.get();
	}
	
	@Override
	public SessionToken getSessionTokenById(UUID tokenId) {
		try {
			return sessionTokenRepository.findById(tokenId).get();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of SessionToken : " + e.getMessage());
		}
	}
	
	@Override
	public SessionToken findByUserIdAndTokenTypeName(UUID userId, String tokenTypeName) {
		
		List<SessionToken> sessionTokens = sessionTokenRepository.findByUserUserIdAndTokenTypeName(userId, tokenTypeName).get();
		
		if (sessionTokens.size() == 0) { throw new TokenSessionNotFoundException(userId, tokenTypeName); }
		
		SessionToken moreRecentToken = sessionTokens.get(0);
		
		for (SessionToken sessionToken  : sessionTokens) {
			if (sessionToken.getCreatedAt().isAfter(moreRecentToken.getCreatedAt())) {
				moreRecentToken = sessionToken;
			}
		}
		
		return moreRecentToken;
	}
	
	@Override
	public List<SessionToken> findAllSessionTokenByUsername(String username) {
		try {
			return sessionTokenRepository.findAllByUserUsername(username).get();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of SessionToken : " + e.getMessage());
		}
	}
	
	@Override
	public List<SessionToken> findAllSessionTokenByTokenType(String tokenType) {
		try {
			return sessionTokenRepository.findAllByTokenTypeName(tokenType).get();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of SessionToken : " + e.getMessage());
		}
	}


	@Override
	public List<SessionToken> findAllSessionToken() {
		try {
			return sessionTokenRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get of SessionToken : " + e.getMessage());
		}
	}
	
	@Override
	public void postSessionToken(UserAccount user, String tokenString, String tokenType) {
		TokenType type = tokenTypeService.getTokenTypeByName(tokenType, true);
		Instant expirationDate = Instant.now().plusMillis(type.getTokenExpirationTime());
		String hash = hashToken(tokenString);
		
		try {
			sessionTokenRepository.save(SessionToken.builder()
				.tokenHash(hash)
				.expirationDate(expirationDate)
				.user(user)
				.tokenType(type)
				.build()
			);
					
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of SessionToken : " + e.getMessage());
		}
	}

	@Override
	public void consumeSessionToken(SessionToken sessionToken) {
		try {
			sessionToken.setConsumed(true);
			sessionTokenRepository.save(sessionToken);			
		} catch (Exception e) {
			throw new UpdateFailedException("Error of update during checkToken : " + e.getMessage());
		}
	}

	@Override
	public void revokeSessionToken(SessionToken sessionToken) {
		try {
			sessionToken.setRevoked(true);
			sessionTokenRepository.save(sessionToken);			
		} catch (Exception e) {
			throw new UpdateFailedException("Error of update during checkToken : " + e.getMessage());
		}
	}
	
	@Override
	public void deleteAllTokenByUserId(UUID userId) {
		
		try {
			sessionTokenRepository.deleteAllByUserUserId(userId);
		} catch (Exception e) {
			throw new DeleteFailedException("Error during deleteAllTokenByUserId : " + e.getMessage());
		}
		
	}
	
	@Override
	public void deleteSessionToken(SessionToken sessionToken) {
		try {
			sessionTokenRepository.delete(sessionToken);
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete SessionToken : " + e.getMessage());
		}
	}
}
