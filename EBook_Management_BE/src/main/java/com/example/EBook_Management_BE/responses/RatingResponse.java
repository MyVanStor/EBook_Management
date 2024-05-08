package com.example.EBook_Management_BE.responses;

import com.example.EBook_Management_BE.entity.Book;
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
public class RatingResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("score")
	private short score;

	@JsonProperty("book")
	private Book book;

	@JsonProperty("user")
	private User user;

}
