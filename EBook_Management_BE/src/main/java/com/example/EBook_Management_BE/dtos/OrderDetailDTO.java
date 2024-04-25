package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.Positive;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderDetailDTO {
	@NegativeOrZero(message = MessageKeyValidation.BOOK_PRICE_NEGATIVE)
	double price;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("book_id")
	Long bookId;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("order_id")
	Long orderId;
}
