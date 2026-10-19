package com.ludo.ms_user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
	private String profileImageKey;
	private String firstname;
	private String lastname;
	private LocalDate dateOfBirth;
	private String address;
	private String city;
	private String country;
	private String phoneNumber;
}
