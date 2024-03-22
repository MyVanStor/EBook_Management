package com.example.EBook_Management_BE.modules.order.response;

import java.util.List;

import com.example.EBook_Management_BE.common.entity.Order;
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
public class OrderResponse {
	@JsonProperty("message")
	private String message;

	@JsonProperty("errors")
	private List<String> errors;

	@JsonProperty("order")
	private Order order;
}
