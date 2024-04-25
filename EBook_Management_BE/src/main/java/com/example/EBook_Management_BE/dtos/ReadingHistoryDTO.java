package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ReadingHistoryDTO {
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	Long userId;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("chapter_id")
	Long chapterId;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("book_id")
	Long bookId;
}
