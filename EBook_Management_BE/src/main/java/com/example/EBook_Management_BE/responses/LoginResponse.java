package com.example.EBook_Management_BE.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
	@JsonProperty("token")
	private String token;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("phone_number")
	private String username;

	@JsonProperty("roles")
	private List<String> roles;
}
