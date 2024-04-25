package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.validations.user.ValidPassword;
import com.example.EBook_Management_BE.validations.user.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDTO {
	@ValidPhoneNumber
	@JsonProperty("phone_number")
	String phoneNumber;

	@ValidPassword
	String password;
}
