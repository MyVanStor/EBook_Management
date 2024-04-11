package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.UserBookDTO;
import com.example.EBook_Management_BE.entity.UserBook;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserBookMapper {
	UserBookMapper iNSTANCE = Mappers.getMapper(UserBookMapper.class);
	
	UserBook mapToUserBookEntity(UserBookDTO userBookDTO);
}
