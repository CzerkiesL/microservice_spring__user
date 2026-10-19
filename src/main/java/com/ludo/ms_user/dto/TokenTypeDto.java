package com.ludo.ms_user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenTypeDto {
	private String name;
	private String description;
	private Long tokenExpirationTime;
}
