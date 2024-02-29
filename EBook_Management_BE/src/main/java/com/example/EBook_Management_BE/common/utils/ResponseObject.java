package com.example.EBook_Management_BE.common.utils;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseObject {
	@JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("data")
    private Object data;
}
