package com.ludo.ms_user.dto.user_api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequestDto {
	
	@Pattern(regexp = "[a-zA-Z0-9_]{3,20}$", message = "/user : username is not valid.")
	private String username;
	
	@Email(message = "/user : email is not valid.")
	@Size(max = 254, message = "/user : email is too long.")
	private String email;
	
	@Size(min = 15, max = 128, message = "/user : password must have between 15 and 128 characters.")
	private String password;
	
	
	@Size(min = 15, max = 128, message = "/user : confirmPassword must have between 15 and 128 characters.")
	private String confirmPassword;
	
	private String firstname;
	private String lastname;
	
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "/user Date of birth must use the format yyyy-MM-dd")
	private String dateOfBirth;
	
	private String address;
	private String city;
	private String country;
	
	@Pattern(regexp = "^\\+?[0-9\\s.-]{6,20}$", message = "/user Phone number is not valid")
	private String phoneNumber;
}
