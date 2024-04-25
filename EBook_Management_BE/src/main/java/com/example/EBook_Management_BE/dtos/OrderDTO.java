package com.example.EBook_Management_BE.dtos;

import java.util.Set;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
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
public class OrderDTO {
	@JsonProperty("status")
	String status;
	
	@JsonProperty("payment_method")
	String paymentMethod;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	Long userId;
	
	@JsonProperty("book_ids")
	Set<Long> bookIds;
}
