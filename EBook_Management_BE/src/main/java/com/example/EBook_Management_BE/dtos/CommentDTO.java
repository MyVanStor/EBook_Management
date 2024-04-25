package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class CommentDTO {
	@NotBlank(message = MessageKeyValidation.COMMENT_NOT_BLANK)
	@JsonProperty("comment")
	String comment;
		
	@JsonProperty("reply_type")
	String replyType;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("reply_id")
	Long replyId;
	
	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	Long userId;
}
