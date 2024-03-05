package com.example.EBook_Management_BE.modules.book.response;

import java.util.Set;

import com.example.EBook_Management_BE.common.entity.Author;
import com.example.EBook_Management_BE.common.entity.Category;
import com.example.EBook_Management_BE.common.entity.OrderDetail;
import com.example.EBook_Management_BE.common.entity.Painter;
import com.example.EBook_Management_BE.common.entity.Rating;
import com.example.EBook_Management_BE.common.entity.UserBook;
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
	
	@JsonProperty("evaluate")
	double evaluate;

	@JsonProperty("thumbnail")
	String thumbnail;

	@JsonProperty("price")
	double price;
	
	@JsonProperty("authors")
	Set<Author> authors;

	@JsonProperty("painters")
	Set<Painter> painters;

	@JsonProperty("categories")
	Set<Category> categories;

	@JsonProperty("order_details")
	Set<OrderDetail> orderDetails;

	@JsonProperty("user_books")
	Set<UserBook> userBooks;

	@JsonProperty("ratings")
	Set<Rating> ratings;
}
