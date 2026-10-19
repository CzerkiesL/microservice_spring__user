package com.ludo.ms_user.exception;

import com.ludo.ms_user.dto.common.ApiResponse;

import com.ludo.ms_user.exception.session_token.TokenAlreadyConsumedException;
import com.ludo.ms_user.exception.session_token.TokenAlreadyRevokedException;
import com.ludo.ms_user.exception.session_token.TokenRevokedException;
import com.ludo.ms_user.exception.session_token.TokenSessionInvalidException;
import com.ludo.ms_user.exception.session_token.TokenSessionNotFoundException;

import com.ludo.ms_user.exception.token_type.TokenTypeAlreadyExistException;
import com.ludo.ms_user.exception.token_type.TokenTypeNotFoundException;

import com.ludo.ms_user.exception.user_account.PasswordNotCorrespondException;
import com.ludo.ms_user.exception.user_account.UserAlreadyEnabledException;
import com.ludo.ms_user.exception.user_account.UserBannedException;
import com.ludo.ms_user.exception.user_account.UserCredentialsException;
import com.ludo.ms_user.exception.user_account.UserNotFoundExeception;
import com.ludo.ms_user.exception.user_account.UsernameOrEmailAlreadyExistException;

import com.ludo.ms_user.exception.user_profile.ProfileImageException;
import com.ludo.ms_user.exception.user_profile.UserProfileAlreadyExistException;
import com.ludo.ms_user.exception.user_profile.UserProfileNotFoundException;

import com.ludo.ms_user.exception.banned_user.BannedUserAlreadyExistException;
import com.ludo.ms_user.exception.banned_user.BannedUserNotFoundException;

import com.ludo.ms_user.exception.global.DataCantBeDeletedException;
import com.ludo.ms_user.exception.global.FileStorageException;
import com.ludo.ms_user.exception.global.InvalidRefreshTokenException;
import com.ludo.ms_user.exception.global.InvalidAccessTokenException;

import com.ludo.ms_user.exception.http_request.CreationFailedException;
import com.ludo.ms_user.exception.http_request.DeleteFailedException;
import com.ludo.ms_user.exception.http_request.GetFailedException;
import com.ludo.ms_user.exception.http_request.UpdateFailedException;

import com.ludo.ms_user.exception.permission.PermissionAlreadyExistExcption;
import com.ludo.ms_user.exception.permission.PermissionAlreafyAssignException;
import com.ludo.ms_user.exception.permission.PermissionNotAssignException;
import com.ludo.ms_user.exception.permission.PermissionNotFoundException;

import com.ludo.ms_user.exception.role.RoleAlreadyAssignException;
import com.ludo.ms_user.exception.role.RoleAlreadyExistException;
import com.ludo.ms_user.exception.role.RoleNotAssignException;
import com.ludo.ms_user.exception.role.RoleNotFoundException;

import com.ludo.ms_user.utils.ResponseBuilder;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	///// GLOBAL
    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidAccessTokenException(InvalidAccessTokenException exception, WebRequest webRequest) {
        return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "INVALID_ACCESS_TOKEN");
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredTokenException(InvalidRefreshTokenException exception, WebRequest webRequest) {
        return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "INVALID_REFRESH_TOKEN");
    }
    
    @ExceptionHandler(DataCantBeDeletedException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataCantBeDeletedException(DataCantBeDeletedException exception, WebRequest webRequest) {
        return ResponseBuilder.error(HttpStatus.FORBIDDEN, exception.getMessage(), "CANT_BE_DELETED");
    }
    
    ///// TOKEN TYPE
    @ExceptionHandler(TokenTypeAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenTypeAlreadyExistException(TokenTypeAlreadyExistException exception) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "TOKEN_TYPE_ALREADY_EXIST");
    }
    
    @ExceptionHandler(TokenTypeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenTypeNotFoundException(TokenTypeNotFoundException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "TOKEN_TYPE_NOT_FOUND");
    }

    ///// USER ACCOUNT
    @ExceptionHandler(UserNotFoundExeception.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundExeception(UserNotFoundExeception exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "USER_NOT_FOUND");
    }

    @ExceptionHandler(UserBannedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserBannedException(UserBannedException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.FORBIDDEN, exception.getMessage(), "USER_BANNED");
    }
    
    @ExceptionHandler(UserAlreadyEnabledException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyEnabledException(UserAlreadyEnabledException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT , exception.getMessage(), "USER_ALREADY_ENABLED");
    }
    
    @ExceptionHandler(UserCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserCredentialsException(UserCredentialsException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "BAD_USER_CREDENTIALS");
    }
    
    ///// SESSION TOKEN
    @ExceptionHandler(TokenRevokedException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenRevokedException(TokenRevokedException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "TOKEN_REVOKED");
    }

    @ExceptionHandler(TokenAlreadyConsumedException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenAlreadyRevokedException(TokenAlreadyRevokedException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "TOKEN_ALREADY_REVOKED");
    }

    @ExceptionHandler(TokenAlreadyRevokedException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenAlreadyConsumedException(TokenAlreadyConsumedException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "TOKEN_ALREADY_CONSUMED");
    }
    
    @ExceptionHandler(TokenSessionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenSessionNotFoundException(TokenSessionNotFoundException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "SESSION_TOKEN_NOT_FOUND");
    }
    
    @ExceptionHandler(TokenSessionInvalidException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenSessionInvalidException(TokenSessionInvalidException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.UNAUTHORIZED, exception.getMessage(), "SESSION_TOKEN_INVALID");
    }

    ///// ROLE
    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleAlreadyExistException(RoleAlreadyExistException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "ROLE_ALREADY_EXIST");
    }
    
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotFoundException(RoleNotFoundException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "ROLE_NOT_FOUND");
    }
    
    @ExceptionHandler(RoleNotAssignException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotAssignException(RoleNotAssignException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "ROLE_NOT_ASSIGN");
    }
    
    @ExceptionHandler(RoleAlreadyAssignException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleAlreadyAssignException(RoleAlreadyAssignException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "ROLE_ALREADY_ASSIGN");
    }
    
    ///// PERMISSION
    @ExceptionHandler(PermissionAlreadyExistExcption.class)
    public ResponseEntity<ApiResponse<Void>> handlePermissionAlreadyExistExcption(PermissionAlreadyExistExcption exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "PERMISSION_ALREADY_EXIST");
    }
    
    @ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePermissionNotFoundException(PermissionNotFoundException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "PERMISSION_NOT_FOUND");
    }
    
    @ExceptionHandler(PermissionAlreafyAssignException.class)
    public ResponseEntity<ApiResponse<Void>> handlePermissionAlreafyAssignException(PermissionAlreafyAssignException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "PERMISSION_ALREADY_ASSIGN");
    }
    
    @ExceptionHandler(PermissionNotAssignException.class)
    public ResponseEntity<ApiResponse<Void>> handlePermissionNotAssignException(PermissionNotAssignException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "PERMISSION_NOT_ASSIGN");
    }

    ///// USERP ROFILE
    @ExceptionHandler(UserProfileAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserProfileAlreadyExistException(UserProfileAlreadyExistException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "USER_PROFILE_ALREADY_EXIST");
    }
    
    @ExceptionHandler(UserProfileNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserProfileNotFoundException(UserProfileNotFoundException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "USER_PROFILE_NOT_FOUND");
    }
    
    @ExceptionHandler(ProfileImageException.class)
    public ResponseEntity<ApiResponse<Void>> handleProfileImageException(ProfileImageException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.BAD_REQUEST, exception.getMessage(), "IMAGE_PROFILE_INVALID");
    }
    
    ///// BANNED USER
    @ExceptionHandler(BannedUserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleBannedUserAlreadyExistException(BannedUserAlreadyExistException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.CONFLICT, exception.getMessage(), "BANNED_USER_ALREADY_EXIST");
    }
    
    @ExceptionHandler(BannedUserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBannedUserNotFoundException(BannedUserNotFoundException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(), "BANNED_USER_NOT_FOUND");
    }
    
    ///////////////////////////// CHAMP DE VALIDATION
    @ExceptionHandler(UsernameOrEmailAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameOrEmailAlreadyExistException(UsernameOrEmailAlreadyExistException exception, WebRequest webRequest) {
        return ResponseBuilder.error(
            HttpStatus.CONFLICT, exception.getMessage(),
            List.of(ApiResponse.ApiError.builder()
        		.field("username/email").message("USERNAME_OR_EMAIL_ALREADY_EXIST").build()
    		)
        );
    }
    
    @ExceptionHandler(PasswordNotCorrespondException.class)
    public ResponseEntity<ApiResponse<Void>> handlePasswordNotCorrespondException(PasswordNotCorrespondException exception) {
        return ResponseBuilder.error(
            HttpStatus.BAD_REQUEST, exception.getMessage(),
            List.of(ApiResponse.ApiError.builder()
    				.field("confirmPassword").message("PASSWORDS_NOT_CORRESPOND").build()
    		)
        );
    }
    

    
    /////////////////////////// CRUD METHOD
    @ExceptionHandler(CreationFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleCreationFailedException(CreationFailedException exception, WebRequest webRequest) {
        return ResponseBuilder.error(
            HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "CREATION_FAILED_EXCEPTION"
		);
    }
    
    @ExceptionHandler(GetFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleGetFailedException(GetFailedException exception, WebRequest webRequest) {
        return ResponseBuilder.error(
            HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "GET_FAILED_EXCEPTION"
		);
    }
    
    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUpdataeFailedException(UpdateFailedException exception, WebRequest webRequest) {
        return ResponseBuilder.error(
            HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "UPDATE_FAILED_EXCEPTION"
		);
    }
    
    @ExceptionHandler(DeleteFailedException.class)
    public ResponseEntity<ApiResponse<Void>> handleDeleteFailedException(DeleteFailedException exception, WebRequest webRequest) {
        return ResponseBuilder.error(
            HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "DELETE_FAILED_EXCEPTION"
		);
    }
    
    /////////////////////////// GENERIC
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileStorageException(FileStorageException exception, WebRequest webRequest) {
    	return ResponseBuilder.error(
    			HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "FILE_STORAGE_EXCEPTION"
    			);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception exception, WebRequest webRequest) {
        return ResponseBuilder.error(
            HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "INTERNAL_SERVER_ERROR"
		);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult()
            .getFieldErrors().stream()
            .map(err -> ApiResponse.ApiError.builder()
                .field(err.getField()).message(err.getDefaultMessage())
                .build()).toList();

        return ResponseBuilder.error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException exception) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
            .status("error")
            .message("You do not have permission to perform this action")
            .data(null)
            .errors(List.of(
                ApiResponse.ApiError.builder()
                    .field(null)
                    .message("FORBIDDEN")
                    .build()
            ))
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}