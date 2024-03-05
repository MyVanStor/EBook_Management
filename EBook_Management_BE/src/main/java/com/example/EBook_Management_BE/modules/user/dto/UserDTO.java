package com.example.EBook_Management_BE.modules.user.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
	String fullname;

	String password;
	
	@JsonProperty("link_avatar")
	String linkAvatar;
	
	@JsonProperty("phone_number")
	String phoneNumber;
	
	short gender;
	
	double budget;

	@JsonProperty("date_of_birth")
	Date dateOfBirth;
	
	@JsonProperty("facebook_account_id")
	String facebookAccountId;
	
	@JsonProperty("google_account_id")
	String googleAccountId;
	
	@JsonProperty("is_active")
	short isActive;
	
	@JsonProperty("role_id")
	Long roleId;
}
