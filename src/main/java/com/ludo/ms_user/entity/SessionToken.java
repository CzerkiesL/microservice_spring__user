package com.ludo.ms_user.entity;

import java.time.Instant;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID tokenId;
	
	@Column(name = "token_hash", nullable = false, length = 255)
	private String tokenHash;
	
	@Column(nullable = false)
	private Instant expirationDate;
	
	@Builder.Default
	@Column(name = "consumed", nullable = false)
	private boolean consumed = false;
	
	@Builder.Default
	@Column(name = "revoked", nullable = false)
	private boolean revoked = false;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
	
	@PrePersist
	private void onCreate() {
		Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
	}
	
	@PreUpdate
	private void onUpdate() {
		updatedAt = Instant.now();
	}
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserAccount user;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "token_type_id")
	private TokenType tokenType;

}
