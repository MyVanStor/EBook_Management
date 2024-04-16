package com.example.EBook_Management_BE.responses;

import com.example.EBook_Management_BE.entity.Book;
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
public class RatingResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("score")
	short score;
	
	@JsonProperty("book")
	Book book;
	
	@JsonProperty("user")
	User user;

}
