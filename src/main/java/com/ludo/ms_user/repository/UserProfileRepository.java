package com.ludo.ms_user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ludo.ms_user.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

}
