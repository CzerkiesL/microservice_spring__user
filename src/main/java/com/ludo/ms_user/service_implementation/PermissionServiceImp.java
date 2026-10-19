package com.ludo.ms_user.service_implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ludo.ms_user.entity.Permission;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;

import com.ludo.ms_user.exception.permission.PermissionAlreadyExistExcption;
import com.ludo.ms_user.exception.permission.PermissionNotFoundException;

import com.ludo.ms_user.repository.PermissionRepository;

import com.ludo.ms_user.service_interface.PermissionServiceInt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImp implements PermissionServiceInt {
	
	private final PermissionRepository permissionRepository;
	
	@Override
	public Permission getPermissionByCode(String code, boolean get) {
		Optional<Permission> permission = permissionRepository.findByCode(code);
		
		if (get) {
			if (permission.isEmpty()) {throw new PermissionNotFoundException(code);}
			return permission.get()
;		} else {
			if (permission.isPresent()) {throw new PermissionAlreadyExistExcption(code);}
		}
		return null;
	}

	@Override
	public List<Permission> getAllPermission() {
		try {
			return permissionRepository.findAll();
		} catch (Exception e) {
			throw new GetFailedException("Error during get all Permission : " + e.getMessage());
		}
	}

	@Override
	public Permission postPermission(Permission permission) {
		try {
			return permissionRepository.save(permission);
		} catch (Exception e) {
			throw new CreationFailedException("Error during creation of Role : " + e.getMessage()); 
		}
	}

	@Override
	public Permission updatePermission(Permission permission) {
		try {
			return permissionRepository.save(permission);
		} catch (Exception e) {
			throw new UpdateFailedException("Error during update of Permission : " + e.getMessage());
		}
	}

	@Override
	public String deletePermission(Permission permission) {
		try {
			permissionRepository.delete(permission);
			return "Permission " + permission.getCode() + " delete with success";
		} catch (Exception e) {
			throw new DeleteFailedException("Error during delete of Permission : " + e.getMessage());
		}
	}

}
