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
@Table(name = "banned_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class BannedUser {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID banId;
	
	@Column(name = "ban_end_date", nullable = false)
	private Instant banEndDate;
	
	@Column(name = "ban_email", nullable = false, unique = true)
	private String banEmail;
	
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
}
