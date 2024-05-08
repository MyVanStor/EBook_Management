package com.example.EBook_Management_BE.dtos;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class AuthorDTO {
	@NotBlank(message = MessageKeyValidation.AUTHOR_NAME_NOT_BLANK)
	private String name;

	@Positive(message = MessageKeyValidation.ID_POSITIVE)
	@JsonProperty("user_id")
	private Long userId;
}
