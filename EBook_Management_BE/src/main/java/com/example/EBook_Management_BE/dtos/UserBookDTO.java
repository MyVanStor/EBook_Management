package com.example.EBook_Management_BE.dtos;

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
public class UserBookDTO {
	private String status;

	private Long bookId;

	private Long userId;
}
