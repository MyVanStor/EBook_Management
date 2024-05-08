package com.example.EBook_Management_BE.responses;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Order;
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
public class OrderDetailResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("price")
	private double price;

	@JsonProperty("book")
	private Book book;

	@JsonProperty("order")
	private Order order;

}
