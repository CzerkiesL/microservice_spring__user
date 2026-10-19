package com.ludo.ms_user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ludo.ms_user.entity.TokenType;

public interface TokenTypeRepository extends JpaRepository<TokenType, UUID> {
	Optional<TokenType> findByName(String name);
}
