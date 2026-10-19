package com.ludo.ms_user.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID roleId;

	@Column(nullable = false, unique = true, length = 50)
	private String name;

	@Column(length = 255)
	private String description;

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

	@ManyToMany(mappedBy = "roles")
	private List<UserAccount> users;

	@ManyToMany
	@JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<Permission> permissions;
}
