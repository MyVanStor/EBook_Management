package com.example.EBook_Management_BE.dtos;

import java.util.Date;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.example.EBook_Management_BE.validations.user.ValidFullName;
import com.example.EBook_Management_BE.validations.user.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.PastOrPresent;
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
	@ValidFullName
	String fullname;

	@ValidPassword
	String password;
	
	@JsonProperty("retype_password")
    private String retypePassword;
	
	@JsonProperty("link_avatar")
	String linkAvatar;
	
	short gender;
	
	@PastOrPresent(message = MessageKeyValidation.USER_DATE_OF_BIRTH)
	@JsonProperty("date_of_birth")
	Date dateOfBirth;
	
	@JsonProperty("facebook_account_id")
	int facebookAccountId;
	
	@JsonProperty("google_account_id")
	int googleAccountId;
	
	@JsonProperty("is_active")
	short isActive;
}
