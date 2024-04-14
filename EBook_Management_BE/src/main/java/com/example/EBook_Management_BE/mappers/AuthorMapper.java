package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.AuthorDTO;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.responses.AuthorResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
	AuthorMapper iNSTANCE = Mappers.getMapper(AuthorMapper.class);
	
	Author mapToAuthorEntity(AuthorDTO authorDTO);
	
	Author mapToAuthorEntity(AuthorResponse authorResponse);
	
	AuthorResponse mapToAuthorResponse(Author author);
}
