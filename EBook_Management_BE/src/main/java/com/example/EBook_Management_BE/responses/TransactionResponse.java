package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("code")
	private String code;

	@JsonProperty("value")
	private Double value;

	@JsonProperty("type")
	private String type;

	@JsonProperty("status")
	private String status;

	@JsonProperty("user")
	private User user;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
}
