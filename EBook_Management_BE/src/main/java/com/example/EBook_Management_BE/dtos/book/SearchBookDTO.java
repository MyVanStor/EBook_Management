package com.example.EBook_Management_BE.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchBookDTO {
    private String status;

    @JsonProperty("type_of_book")
    private String typeOfBook;

    private List<Long> categoryIds;

    private String keyword;
}
