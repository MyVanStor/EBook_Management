package com.example.EBook_Management_BE.modules.category.response;

import java.util.List;

import com.example.EBook_Management_BE.common.entity.Category;
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
public class CategoryResponse {
	@JsonProperty("message")
	private String message;

	@JsonProperty("errors")
	private List<String> errors;

	@JsonProperty("category")
	private Category category;
}
