package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	@NotBlank(message = MessageKeyValidation.COMMENT_NOT_BLANK)
	@JsonProperty("comment")
	private String comment;

	@JsonProperty("reply_type")
	private String replyType;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("reply_id")
	private Long replyId;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	private Long userId;
}
