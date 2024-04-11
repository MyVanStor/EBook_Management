package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.BookDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.responses.BookResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
	BookMapper iNSTANCE = Mappers.getMapper(BookMapper.class);

	Book mapToBookEntity(BookDTO bookDTO);
	
	BookResponse mapToBookResponse(Book book);
}
