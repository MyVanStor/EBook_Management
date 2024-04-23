package com.example.EBook_Management_BE.responses;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.entity.UserBook;
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
public class ReadingHistoryResponse {
	@JsonProperty("id")
	Long id;
	
	@JsonProperty("user")
	User user;
	
	@JsonProperty("chapter")
	Chapter chapter;
	
	@JsonProperty("book")
	Book book;
	
	@JsonProperty("created_at")
	LocalDateTime createdAt;
	
	@JsonProperty("updated_at")
	LocalDateTime updatedAt;
}
