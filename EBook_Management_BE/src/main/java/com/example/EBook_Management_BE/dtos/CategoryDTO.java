package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	@NotBlank(message = MessageKeyValidation.CATEGORY_NAME_NOT_BLANK)
	private String name;

	private String description;
}
