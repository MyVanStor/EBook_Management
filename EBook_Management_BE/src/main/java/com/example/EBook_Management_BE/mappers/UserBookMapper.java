package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.UserBookDTO;
import com.example.EBook_Management_BE.entity.UserBook;

@Mapper(componentModel = "spring")
public interface UserBookMapper {
	UserBookMapper INSTANCE = Mappers.getMapper(UserBookMapper.class);
	
	UserBook mapToUserBookEntity(UserBookDTO userBookDTO);
	
	
}
