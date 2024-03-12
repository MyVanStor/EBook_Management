package com.example.EBook_Management_BE.modules.user.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
	String fullname;

	String password;
	
	@JsonProperty("retype_password")
    private String retypePassword;
	
	@JsonProperty("link_avatar")
	String linkAvatar;
	
	@JsonProperty("phone_number")
	String phoneNumber;
	
	short gender;
	
	@JsonProperty("date_of_birth")
	Date dateOfBirth;
	
	@JsonProperty("facebook_account_id")
	int facebookAccountId;
	
	@JsonProperty("google_account_id")
	int googleAccountId;
	
	@JsonProperty("is_active")
	short isActive;
}
