package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;
import java.util.Date;

import com.example.EBook_Management_BE.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("fullname")
	private String fullname;

	@JsonProperty("password")
	private String password;

	@JsonProperty("link_avatar")
	private String linkAvatar;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@JsonProperty("gender")
	private short gender;

	@JsonProperty("budget")
	private double budget;

	@JsonProperty("date_of_birth")
	private String dateOfBirth;

	@JsonProperty("is_active")
	private short isActive;

	@JsonProperty("role")
	private Role role;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
}
