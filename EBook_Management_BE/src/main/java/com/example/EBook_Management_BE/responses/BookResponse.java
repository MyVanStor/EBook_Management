package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.entity.UserBook;
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
public class BookResponse {
	@JsonProperty("id")
	Long id;

	@JsonProperty("title")
	String title;

	@JsonProperty("summary")
	String summary;
	
	@JsonProperty("image")
	String image;
	
	@JsonProperty("type_of_book")
	String typeOfBook;

	@JsonProperty("publishing_year")
	Integer publishingYear;

	@JsonProperty("price")
	double price;
	
	@JsonProperty("created_at")
	LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	LocalDateTime updatedAt;
	
	@JsonProperty("authors")
	Set<Author> authors;

	@JsonProperty("painters")
	Set<Painter> painters;

	@JsonProperty("categories")
	Set<Category> categories;
	
	@JsonProperty("user_book")
	Set<UserBook> userBooks;

	@JsonProperty("chapters")
	Set<Chapter> chapters;
}
