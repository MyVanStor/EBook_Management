package com.example.EBook_Management_BE.modules.painter.response;

import java.util.List;

import com.example.EBook_Management_BE.common.entity.Painter;
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
public class PainterResponse {
	@JsonProperty("message")
	private String message;

	@JsonProperty("errors")
	private List<String> errors;

	@JsonProperty("category")
	private Painter painter;
}
