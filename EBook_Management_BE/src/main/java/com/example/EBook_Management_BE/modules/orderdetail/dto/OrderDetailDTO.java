package com.example.EBook_Management_BE.modules.orderdetail.dto;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderDetailDTO {
	double price;
	
	@JsonProperty("book_id")
	Long bookId;
	
	@JsonProperty("order_id")
	Long orderId;
}
