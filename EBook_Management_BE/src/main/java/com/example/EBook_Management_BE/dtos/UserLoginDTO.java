package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.validations.user.ValidPassword;
import com.example.EBook_Management_BE.validations.user.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	@ValidPhoneNumber
	@JsonProperty("phone_number")
	private String phoneNumber;

	@ValidPassword
	private String password;
}
