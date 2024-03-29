package com.example.EBook_Management_BE.modules.user.response;

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
	@JsonProperty("message")
	String message;

	@JsonProperty("token")
	String token;

	@JsonProperty("refresh_token")
	String refreshToken;
	
	String tokenType = "Bearer";
	
	// user's detail
	Long id;
	
	String username;

	List<String> roles;
}
