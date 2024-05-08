package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.Book;
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
public class ChapterResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("ordinal_number")
	private int ordinalNumber;

	@JsonProperty("thumbnail")
	private String thumbnail;

	@JsonProperty("book")
	private Book book;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
}
