package com.example.EBook_Management_BE.responses;

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
public class FollowResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("following")
	private Long following;

	@JsonProperty("user")
	private User user;
}
