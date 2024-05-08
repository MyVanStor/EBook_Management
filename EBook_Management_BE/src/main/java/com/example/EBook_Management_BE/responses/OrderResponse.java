package com.example.EBook_Management_BE.responses;

import java.util.Set;

import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.entity.User;
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
	@JsonProperty("id")
	private Long id;

	@JsonProperty("status")
	private String status;

	@JsonProperty("total_money")
	private double totalMoney;

	@JsonProperty("payment_method")
	private String paymentMethod;

	@JsonProperty("order_details")
	private Set<OrderDetail> orderDetails;

	@JsonProperty("user")
	private User user;
}
