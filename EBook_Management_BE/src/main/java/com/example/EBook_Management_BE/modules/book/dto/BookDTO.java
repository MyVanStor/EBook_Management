package com.example.EBook_Management_BE.modules.book.dto;

import java.util.Set;

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
public class BookDTO {
	String title;

	String summary;

	String image;

	@JsonProperty("type_of_book")
	String typeOfBook;

	@JsonProperty("publishing_year")
	Integer publishingYear;

	double evaluate;

	String thumbnail;

	double price;

	Set<Long> categoryIds;

	Set<Long> painterIds;

	Set<Long> authorIds;

	Set<Long> orderDetailIds;

	Set<UserBook> userBooks;

	Set<Rating> ratings;
}
