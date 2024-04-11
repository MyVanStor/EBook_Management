package com.example.EBook_Management_BE.dtos;

import java.util.Set;

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
public class OrderDTO {
	@JsonProperty("status")
	String status;
		
	@JsonProperty("total_money")
	double totalMoney;
	
	@JsonProperty("payment_method")
	String paymentMethod;
	
	@JsonProperty("user_id")
	Long userId;
	
	@JsonProperty("book_ids")
	Set<Long> bookIds;
}
