package com.ludo.ms_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;
import com.ludo.ms_user.entity.TokenType;
import com.ludo.ms_user.entity.UserAccount;
import com.ludo.ms_user.entity.UserProfile;

import com.ludo.ms_user.repository.PermissionRepository;
import com.ludo.ms_user.repository.RoleRepository;
import com.ludo.ms_user.repository.TokenTypeRepository;
import com.ludo.ms_user.repository.UserAccountRepository;
import com.ludo.ms_user.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
	
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final TokenTypeRepository tokenTypeRepository;
	private final UserAccountRepository userAccountRepository;
	private final UserProfileRepository userProfileRepository;
	
	private final JwtProperties jwtProperties;
	private final TransactionTemplate transactionTemplate;
	private final BCryptPasswordEncoder bCrypt;
	private final MsUserConfiguration msUserConfiguration;
	
	private Role createRoleIfMissing(String name, String description, List<Permission> permissions) {
        return roleRepository.findByName(name)
    		.orElseGet(() -> roleRepository.save(
                Role.builder()
                    .name(name)
                    .description(description)
                    .canBeDeleted(false)
                    .permissions(new ArrayList<>(permissions))
                    .build()
            )
		);
    }
	
	private Permission createPermissionIfMissing(String code, String description) {
		return permissionRepository.findByCode(code)
			.orElseGet(() -> permissionRepository.save(
				Permission.builder()
					.code(code)
					.description(description)
					.canBeDeleted(false)
					.build()
			)
		);
	}
	
	private TokenType createTokenTypeIfMissing(String name, String description, Long tokenExpirationTime) {
		return tokenTypeRepository.findByName(name)
			.orElseGet(() -> tokenTypeRepository.save(
				TokenType.builder()
					.name(name)
					.description(description)
					.tokenExpirationTime(tokenExpirationTime)
					.canBeDeleted(false)
					.build()
			)
		);
	}
	
	private UserAccount createUserAccountIfMissing(String username, String email, String password, List<Role> roles) {
		return userAccountRepository.findByUsername(username)
			.orElseGet(() -> userAccountRepository.save(
				UserAccount.builder()
					.username(username)
					.email(email)
					.passwordHash(bCrypt.encode(password))
					.enabled(true)
					.canBeDeleted(false)
					.roles(roles)
					.build()
			)
		);
	}
	
	private UserProfile createUserProfileIfMissing(UserAccount userAccount) {
		return userProfileRepository.findById(userAccount.getUserId())
			.orElseGet(() -> userProfileRepository.save(
				UserProfile.builder()
					.user(userAccount)
					.build()
			)
		);
	}
	
	@Bean
	@SuppressWarnings("unused")
    CommandLineRunner initializeRolesAndPermissions() {
        return _ -> transactionTemplate.executeWithoutResult(status -> {
        	initializeRolesAndPermission();
        	initializeTokenType();
        });
    }
	
	private void initializeRolesAndPermission() {
		createPermissionIfMissing("user:readAll", "Read all user account");
		
		createPermissionIfMissing("role:create", "Create roles");
		createPermissionIfMissing("role:read", "Read roles");
		createPermissionIfMissing("role:update", "Update roles");
		createPermissionIfMissing("role:delete", "Delete roles");
		
		createPermissionIfMissing("tokenType:create", "Create tokenType");
		createPermissionIfMissing("tokenType:read", "Read tokenType");
        createPermissionIfMissing("tokenType:update", "Update tokenType");
        createPermissionIfMissing("tokenType:delete", "Delete tokenType");
        
        createPermissionIfMissing("permission:create", "Create permission");
        createPermissionIfMissing("permission:read", "Read permission");
        createPermissionIfMissing("permission:update", "Update permission");
        createPermissionIfMissing("permission:delete", "Delete permission");
        
        createPermissionIfMissing("sessionToken:create", "create sessionToken");
        createPermissionIfMissing("sessionToken:read", "read sessionToken");
		createPermissionIfMissing("sessionToken:update", "update sessionToken");
		createPermissionIfMissing("sessionToken:delete", "delete sessionToken");
		
		createPermissionIfMissing("bannedUser:create", "create BannedUser");
		createPermissionIfMissing("bannedUser:read", "read BannedUser");
		createPermissionIfMissing("bannedUser:update", "update BannedUser");
		createPermissionIfMissing("bannedUser:delete", "delete BannedUser");

		Permission userReadSelf = createPermissionIfMissing(
			"user:readSelf", "Read own account"
		);
		Permission userUpdateSelf = createPermissionIfMissing(
            "user:updateSelf", "Update own account"
        );
        Permission userDeleteSelf = createPermissionIfMissing(
            "user:deleteSelf", "Delete own account"
        );
        Permission userBan = createPermissionIfMissing(
            "user:ban", "Ban a user account"
        );
        Permission userUnban = createPermissionIfMissing(
            "user:unban", "Unban a user account"
        );
        Permission userManageRole = createPermissionIfMissing(
    		"user:manageRole", "manage role for user account"
		);
        Permission roleManagePermission = createPermissionIfMissing(
    		"role:managePermission", "Manage roles permissions"
		);
		Permission userCrud = createPermissionIfMissing(
			"user:crud", "create / read / updeta / delete User"
		);
		Permission permissionCrud = createPermissionIfMissing(
			"permission:crud", "create / read / updeta / delete Permission"
		);
		Permission roleCrud = createPermissionIfMissing(
			"role:crud", "create / read / updeta / delete Role"
		);
		Permission tokenTypeCrud = createPermissionIfMissing(
			"tokenType:crud", "create / read / updeta / delete TokenType"
		);
		Permission sessionTokenCrud = createPermissionIfMissing(
			"sessionToken:crud", "create / read / updeta / delete SessionToken"
		);
		
		Permission bannedUserCrud = createPermissionIfMissing(
				"bannedUser:crud", "create / read / updeta / delete BannedUser"
				);
		
        Role user = createRoleIfMissing(
    		"USER", "Utilisateur de base.", 
    		List.of(
				userReadSelf, userUpdateSelf, userDeleteSelf
			)
		);
        
		Role superAdmin = createRoleIfMissing(
			"SUPER_ADMIN", "Utilisateur administrateur de la base de données.", 
			List.of(
				userCrud, userBan, userUnban, userManageRole, roleCrud,
				roleManagePermission, tokenTypeCrud, permissionCrud, 
				sessionTokenCrud, bannedUserCrud
			)
		);
		
		UserAccount superAdminAccount = createUserAccountIfMissing(
			msUserConfiguration.getSuperAdminUsername(),
			msUserConfiguration.getSuperAdminEmail(),
			msUserConfiguration.getSuperAdminPassword(),
			List.of(
				user, superAdmin
			)
		);
		
		createUserProfileIfMissing(superAdminAccount);
				
	}
	
	private void initializeTokenType() {
		createTokenTypeIfMissing(
			"REFRESH_TOKEN", 
			"token servant a rafraichir la connection d'un utilisateur", 
			jwtProperties.getRefreshDuration()
		);
		createTokenTypeIfMissing(
			"ACCOUNT_ACTIVATION", 
			"token servant a activer le compte d'un utilisateur", 
			900000L
		);
		createTokenTypeIfMissing(
			"RESET_PASSWORD", 
			"token servant a autoriser la modification du mot de passe d'un utilisateur", 
			900000L
		);
	}
}
