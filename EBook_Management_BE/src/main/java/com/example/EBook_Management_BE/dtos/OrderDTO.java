package com.example.EBook_Management_BE.dtos;

import java.util.Set;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	@JsonProperty("status")
	private String status;

	@JsonProperty("payment_method")
	private String paymentMethod;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("book_ids")
	private Set<Long> bookIds;
}
