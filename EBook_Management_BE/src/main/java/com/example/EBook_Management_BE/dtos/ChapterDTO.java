package com.example.EBook_Management_BE.dtos;

import org.hibernate.validator.constraints.URL;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterDTO {
	@NotBlank(message = MessageKeyValidation.CHAPTER_NAME_NOT_BLANK)
	@JsonProperty("name")
	String name;

	@NotNull(message = MessageKeyValidation.CHAPTER_ORDINAL_NUMBER_NOT_NULL)
	@Negative(message = MessageKeyValidation.CHAPTER_ORDINAL_NUMBER_NEGATIVE)
	@JsonProperty("ordinal_number")
	int ordinalNumber;

	@URL(message = MessageKeyValidation.CHAPTER_THUMBNAIL_URL)
	@NotBlank(message = MessageKeyValidation.CHAPTER_THUMBNAIL_NOT_BLANK)
	@JsonProperty("thumbnail")
	String thumbnail;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("book_id")
	Long bookId;

}
