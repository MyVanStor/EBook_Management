package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.entity.UserBook;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("summary")
	private String summary;

	@JsonProperty("image")
	private String image;

	@JsonProperty("type_of_book")
	private String typeOfBook;

	@JsonProperty("status")
	private String status;

	@JsonProperty("publishing_year")
	private Integer publishingYear;

	@JsonProperty("price")
	private double price;

	@JsonProperty("number_reads")
	private Long numberReads;

	@JsonProperty("author")
	private String author;

	@JsonProperty("painter")
	private String painter;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;

	@JsonProperty("categories")
	private Set<Category> categories;

	@JsonProperty("user_book")
	private Set<UserBook> userBooks;
}
