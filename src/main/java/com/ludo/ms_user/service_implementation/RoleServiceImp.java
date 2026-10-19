package com.ludo.ms_user.service_implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.Permission;
import com.ludo.ms_user.entity.Role;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;
import com.ludo.ms_user.exception.role.RoleAlreadyExistException;
import com.ludo.ms_user.exception.role.RoleNotFoundException;

import com.ludo.ms_user.repository.RoleRepository;

import com.ludo.ms_user.service_interface.RoleServiceInt;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleServiceInt {
	
	private final RoleRepository roleRepository;

	@Override
	@Transactional
	public Role getRoleByname(String name, boolean get) {
		Optional<Role> role = roleRepository.findByName(name);
		
		if (get) {
			if (role.isEmpty()) { throw new RoleNotFoundException(name); }
			return role.get();
		} else {
			if (role.isPresent()) { throw new RoleAlreadyExistException(name); }
		}
		return null;
	}
	
	@Override
	public List<Role> getAllRole() {
		try {
			return roleRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get all Role : " + e.getMessage());
		}
	}
	
	@Override
	public List<Role> getRoleByPermissionContaining(Permission permission) {
		try {
			return roleRepository.findByPermissionsContaining(permission).get();
		} catch (Exception e) {
			throw new GetFailedException("Error during get all Role : " + e.getMessage());
		}
	}
	
	@Override
	public Role postRole(String name, String description) {
		try {
			return roleRepository.save(Role.builder().name(name).description(description).build());
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of Role : " + e.getMessage()); 
		}
	}

	@Override
	public Role updateRole(Role role) {
		try {
			return roleRepository.save(role);			
		} catch (Exception e) {
			throw new UpdateFailedException("Error during update of Role : " + e.getMessage());
		}
	}

	@Override
	public String deleteRole(Role role) {
		try {
			roleRepository.delete(role);
			return "Role " + role.getName() + " delete with success";
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete of Role : " + e.getMessage());
		}
		
	}
}
