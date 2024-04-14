package com.example.EBook_Management_BE.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("comment")
	String comment;
		
	@JsonProperty("reply_type")
	String replyType;
	
	@JsonProperty("reply_id")
	Long replyId;
	
	@JsonProperty("user_id")
	Long userId;
}
