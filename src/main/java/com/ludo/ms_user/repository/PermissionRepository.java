package com.ludo.ms_user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ludo.ms_user.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	Optional<Permission> findByCode(String code);
}
