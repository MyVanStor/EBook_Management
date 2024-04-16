package com.example.EBook_Management_BE.responses;

import java.util.Set;

import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.entity.User;
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
public class OrderResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("status")
	String status;
	
	@JsonProperty("total_money")
	double totalMoney;
	
	@JsonProperty("payment_method")
	String paymentMethod;
	
	@JsonProperty("order_details")
	Set<OrderDetail> orderDetails;
	
	@JsonProperty("user")
	User user;
}
