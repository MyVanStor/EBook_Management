package com.example.EBook_Management_BE.dtos;

import java.util.Set;

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

	String thumbnail;

	double price;

	Set<Long> categoryIds;

	Set<Long> painterIds;

	Set<Long> authorIds;
}
