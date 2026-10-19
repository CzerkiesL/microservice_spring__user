package com.ludo.ms_user.utils;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ludo.ms_user.dto.common.ApiResponse;

public final class ResponseBuilder {
	
	private ResponseBuilder() {}
	
	public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus httpStatus, String message, T data) {
		ApiResponse<T> body = ApiResponse.<T>builder()
			.status("success")
			.message(message)
			.data(data)
			.errors(null)
			.build();
		
		return ResponseEntity.status(httpStatus).body(body);
	}
	
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, List<ApiResponse.ApiError> errors) {
        ApiResponse<T> body = ApiResponse.<T>builder()
            .status("error")
            .message(message)
            .data(null)
            .errors(errors)
            .build();
        
        return ResponseEntity.status(status).body(body);
    }
    
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, String errorCode) {
        ApiResponse<T> body = ApiResponse.<T>builder()
            .status("error")
            .message(message)
            .data(null)
            .errors(Collections.singletonList(
                ApiResponse.ApiError.builder()
                    .field(null)
                    .message(errorCode)
                    .build()
    		))
            .build();

        return ResponseEntity.status(status).body(body);
    }
}
