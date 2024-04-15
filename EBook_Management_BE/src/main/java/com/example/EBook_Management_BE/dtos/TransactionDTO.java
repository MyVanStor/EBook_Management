package com.example.EBook_Management_BE.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDTO {
	@JsonProperty("code")
	String code;
	
	@JsonProperty("value")
	Double value;
	
	@JsonProperty("type")
	String type;
	
	@JsonProperty("status")
	String status;
	
	@JsonProperty("user_id")
	Long userId;
}
