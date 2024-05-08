package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("reply_type")
	private String replyType;

	@JsonProperty("reply_id")
	private Long replyId;

	@JsonProperty("user")
	private User user;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;
}
