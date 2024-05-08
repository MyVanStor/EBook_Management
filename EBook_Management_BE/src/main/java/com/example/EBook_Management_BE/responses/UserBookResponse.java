package com.example.EBook_Management_BE.responses;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("status")
	private String status;

	@JsonProperty("user")
	private User user;

	@JsonProperty("book")
	private Book book;
}
