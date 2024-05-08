package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
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
public class ReadingHistoryResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("user")
	private User user;

	@JsonProperty("chapter")
	private Chapter chapter;

	@JsonProperty("book")
	private Book book;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
}
