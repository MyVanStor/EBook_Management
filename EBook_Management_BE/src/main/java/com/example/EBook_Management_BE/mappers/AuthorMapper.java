package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.AuthorDTO;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.responses.AuthorResponse;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
	AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
	
	Author mapToAuthorEntity(AuthorDTO authorDTO);
	
	Author mapToAuthorEntity(AuthorResponse authorResponse);
	
	AuthorResponse mapToAuthorResponse(Author author);
}
