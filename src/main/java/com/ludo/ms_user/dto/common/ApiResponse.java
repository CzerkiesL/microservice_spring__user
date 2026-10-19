package com.ludo.ms_user.dto.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
	
	private String status;
	private String message;
	private T data;
	private List<ApiError> errors;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ApiError {
		private String field;
		private String message;
	}
}
