package com.ludo.ms_user.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "token_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenType {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID tokenTypeId;
	
	@Column(nullable = false, length = 25, unique = true)
	private String name;
	
	@Column(length = 255)
	private String description;
	
	@Column(nullable = false)
	private Long tokenExpirationTime;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@Builder.Default
	@Column(name = "can_be_deleted", nullable = false)
	private boolean canBeDeleted = true;
	
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
}
