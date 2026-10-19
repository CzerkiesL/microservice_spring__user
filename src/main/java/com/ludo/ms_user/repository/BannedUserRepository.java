package com.ludo.ms_user.repository;

import java.time.Instant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ludo.ms_user.entity.BannedUser;

public interface BannedUserRepository extends JpaRepository<BannedUser, UUID> {

	Optional<BannedUser> findByBanEmail(String banEmail);
	
	@Query("""
		SELECT b
		FROM BannedUser b
		WHERE b.banEndDate < :now
	""")
	List<BannedUser> findExpiredBans(@Param("now") Instant now);
}
