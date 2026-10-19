package com.ludo.ms_user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	Optional<Role> findByName(String name);
	Optional<List<Role>> findByPermissionsContaining(Permission permission);
}
