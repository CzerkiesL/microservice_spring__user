package com.ludo.ms_user.dto.admin_api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTokenTypeRequestDto {
	private String description;
	private Long tokenExpirationTime;
}
