package com.example.EBook_Management_BE.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ChapterDTO {
	@JsonProperty("name")
	String name;
	
	@JsonProperty("ordinal_number")
	int ordinalNumber;
	
	@JsonProperty("thumbnail")
	String thumbnail;
	
	@JsonProperty("book_id")
	Long bookId;
	
}
