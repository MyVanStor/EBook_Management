package com.example.EBook_Management_BE.modules.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;

	@NotBlank(message = "Password cannot be blank")
	private String password;

	@Min(value = 1, message = "You must enter role's Id")
	private Long roleId;
}
