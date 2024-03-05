package com.example.EBook_Management_BE.modules.userbook.response;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.User;
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
public class UserBookResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("status")
	String status;
	
	@JsonProperty("user")
	User user;
	
	@JsonProperty("book")
	Book book; 
}
