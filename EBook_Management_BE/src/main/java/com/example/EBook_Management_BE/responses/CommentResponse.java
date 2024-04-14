package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("comment")
	String comment;
	
	@JsonProperty("reply_type")
	String replyType;
	
	@JsonProperty("reply_id")
	Long replyId;
	
	@JsonProperty("user")
	User user;
	
	@JsonProperty("created_at")
	LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	LocalDateTime updatedAt;
}
