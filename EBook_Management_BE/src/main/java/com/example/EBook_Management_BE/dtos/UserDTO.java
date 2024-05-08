package com.example.EBook_Management_BE.dtos;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.example.EBook_Management_BE.validations.user.ValidFullName;
import com.example.EBook_Management_BE.validations.user.ValidPassword;
import com.example.EBook_Management_BE.validations.user.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	@ValidFullName
	private String fullname;

	@ValidPassword
	private String password;

	@JsonProperty("retype_password")
	private String retypePassword;

	@URL(message = MessageKeyValidation.USER_AVATAR_URL)
	@JsonProperty("link_avatar")
	private String linkAvatar;

	@ValidPhoneNumber
	@JsonProperty("phone_number")
	private String phoneNumber;

	private short gender;

	@NegativeOrZero(message = MessageKeyValidation.USER_BUDGET_NEGATIVE)
	private double budget;

	@PastOrPresent(message = MessageKeyValidation.USER_DATE_OF_BIRTH)
	@JsonProperty("date_of_birth")
	private LocalDateTime dateOfBirth;

	@JsonProperty("facebook_account_id")
	private int facebookAccountId;

	@JsonProperty("google_account_id")
	private int googleAccountId;

	@JsonProperty("is_active")
	private short isActive;

	@JsonProperty("role_id")
	private Long roleId;
}
