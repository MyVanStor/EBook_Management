package com.example.EBook_Management_BE.dtos;

import org.hibernate.validator.constraints.Range;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class RatingDTO {
	@Range(min = 0, max = 10, message = MessageKeyValidation.RATING_SCORE_SIZE)
	short score;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("book_id")
	Long bookId;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	Long userId;
}
