package com.example.EBook_Management_BE.dtos;

import java.util.Date;

import org.hibernate.validator.constraints.URL;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.example.EBook_Management_BE.validations.user.ValidFullName;
import com.example.EBook_Management_BE.validations.user.ValidPassword;
import com.example.EBook_Management_BE.validations.user.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.PastOrPresent;
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
    @ValidFullName
	String fullname;
    
    @ValidPassword
	String password;
	
	@JsonProperty("retype_password")
    String retypePassword;
	
	@URL(message = MessageKeyValidation.USER_AVATAR_URL)
	@JsonProperty("link_avatar")
	String linkAvatar;
	
	@ValidPhoneNumber
	@JsonProperty("phone_number")
	String phoneNumber;
	
	short gender;
	
	@NegativeOrZero(message = MessageKeyValidation.USER_BUDGET_NEGATIVE)
	double budget;

	@PastOrPresent(message = MessageKeyValidation.USER_DATE_OF_BIRTH)
	@JsonProperty("date_of_birth")
	Date dateOfBirth;
	
	@JsonProperty("facebook_account_id")
	int facebookAccountId;
	
	@JsonProperty("google_account_id")
	int googleAccountId;
	
	@JsonProperty("is_active")
	short isActive;
	
	@JsonProperty("role_id")
	Long roleId;
}
