package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;

import com.example.EBook_Management_BE.entity.Book;
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
public class ChapterResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("name")
	String name;
	
	@JsonProperty("ordinal_number")
	int ordinalNumber;
	
	@JsonProperty("thumbnail")
	String thumbnail;
	
	@JsonProperty("book")
	Book book;
	
	@JsonProperty("created_at")
	LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	LocalDateTime updatedAt;
}
