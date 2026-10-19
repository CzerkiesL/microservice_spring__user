package com.ludo.ms_user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
	Optional<UserAccount> findByEmail(String email);
	Optional<UserAccount> findByUsername(String username);
	Optional<List<UserAccount>> findByRolesContaining(Role role);
}
