package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.book.BookDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.responses.BookResponse;

@Mapper(componentModel = "spring")
public interface BookMapper {
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

	Book mapToBookEntity(BookDTO bookDTO);

	BookResponse mapToBookResponse(Book book);
}
