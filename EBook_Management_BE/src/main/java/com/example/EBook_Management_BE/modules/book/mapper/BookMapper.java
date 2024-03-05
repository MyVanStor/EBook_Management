package com.example.EBook_Management_BE.modules.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.modules.book.dto.BookDTO;
import com.example.EBook_Management_BE.modules.book.response.BookResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
	BookMapper iNSTANCE = Mappers.getMapper(BookMapper.class);

	Book mapToBookEntity(BookDTO bookDTO);
	
	BookResponse mapToBookResponse(Book book);
}
