package com.example.EBook_Management_BE.dtos;

import java.util.Set;

import org.hibernate.validator.constraints.URL;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	@NotBlank(message = MessageKeyValidation.BOOK_TITLE_NOT_BLANK)
	private String title;

	private String summary;

	private String image;

	@JsonProperty("type_of_book")
	private String typeOfBook;

	@JsonProperty("publishing_year")
	private Integer publishingYear;

	@NotBlank(message = MessageKeyValidation.BOOK_THUMBNAIL_NOT_BLANK)
	@URL(message = MessageKeyValidation.BOOK_THUMBNAIL_URL)
	private String thumbnail;

	@NegativeOrZero(message = MessageKeyValidation.BOOK_PRICE_NEGATIVE)
	private double price;

	@JsonProperty("number_reads")
	private Long numberReads;

	private Set<Long> categoryIds;

	private Set<Long> painterIds;

	private Set<Long> authorIds;
}
