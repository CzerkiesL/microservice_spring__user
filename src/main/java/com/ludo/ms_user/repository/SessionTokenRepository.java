package com.ludo.ms_user.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ludo.ms_user.entity.SessionToken;

public interface SessionTokenRepository extends JpaRepository<SessionToken, UUID> {
	Optional<List<SessionToken>> findByUserUserIdAndTokenTypeName(UUID userId, String tokenTypeName);
	
	Optional<SessionToken> findByTokenHashAndTokenTypeName(String tokenHash, String tokenTypeName);
	
	Optional<List<SessionToken>> findAllByUserUsername(String username);

	Optional<List<SessionToken>> findAllByTokenTypeName(String name);
	
	void deleteAllByUserUserId(UUID userId);
	
	@Query("""
		SELECT t
		FROM SessionToken t
		WHERE t.expirationDate < :now
	""")
	List<SessionToken> findExpiredSessionTokens(@Param("now") Instant now);
}
