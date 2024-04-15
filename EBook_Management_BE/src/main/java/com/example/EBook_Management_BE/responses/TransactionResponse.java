package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class TransactionResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("code")
	String code;
	
	@JsonProperty("value")
	Double value;
	
	@JsonProperty("type")
	String type;
	
	@JsonProperty("status")
	String status;
	
	@JsonProperty("user")
	User user;
	
	@JsonProperty("created_at")
	LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	LocalDateTime updatedAt;
}
