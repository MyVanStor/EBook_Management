package com.example.EBook_Management_BE.dtos;

import java.util.Set;

import org.hibernate.validator.constraints.URL;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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
	@NotBlank(message = MessageKeyValidation.BOOK_TITLE_NOT_BLANK)
	String title;
	
	String summary;
	
	String image;

	@JsonProperty("type_of_book")
	String typeOfBook;

	@JsonProperty("publishing_year")
	Integer publishingYear;
	
	@NotBlank(message = MessageKeyValidation.BOOK_THUMBNAIL_NOT_BLANK)
	@URL(message = MessageKeyValidation.BOOK_THUMBNAIL_URL)
	String thumbnail;
	
	@NegativeOrZero(message = MessageKeyValidation.BOOK_PRICE_NEGATIVE)
	double price;
	
	@JsonProperty("number_reads")
	Long numberReads;

	Set<Long> categoryIds;

	Set<Long> painterIds;

	Set<Long> authorIds;
}
