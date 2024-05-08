package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadingHistoryDTO {
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	private Long userId;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("chapter_id")
	private Long chapterId;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("book_id")
	private Long bookId;
}
