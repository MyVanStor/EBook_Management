package com.example.EBook_Management_BE.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
	@JsonProperty("token")
	String token;

	@JsonProperty("refresh_token")
	String refreshToken;
	
	@JsonProperty("token_type")
	String tokenType;
	
	@JsonProperty("user_id")
	Long userId;
	
	@JsonProperty("user_name")
	String username;

	@JsonProperty("roles")
	List<String> roles;
}
