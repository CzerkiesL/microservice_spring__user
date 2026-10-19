package com.ludo.ms_user.entity;

import java.time.Instant;
import java.time.LocalDate;

import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

	@Id
	private UUID userId;
	
	@Builder.Default
	@Column(name = "profile_image_key", length = 255)
	private String profileImageKey = "defaults/avatar.jpg";
	
	@Column(nullable = true)
	private String firstname;
	
	@Column(nullable = true)
	private String lastname;
		
	@Column(name = "date_of_birth", nullable = true)
	private LocalDate dateOfBirth;
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = true)
	private String city;
	
	@Column(nullable = true)
	private String country;
	
	@Column(name = "phone_number", nullable = true)
	private String phoneNumber;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
	
    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserAccount user;
	
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
