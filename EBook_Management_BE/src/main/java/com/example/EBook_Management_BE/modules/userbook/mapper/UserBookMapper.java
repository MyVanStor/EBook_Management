package com.example.EBook_Management_BE.modules.userbook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.common.entity.UserBook;
import com.example.EBook_Management_BE.modules.userbook.dto.UserBookDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserBookMapper {
	UserBookMapper iNSTANCE = Mappers.getMapper(UserBookMapper.class);
	
	UserBook mapToUserBookEntity(UserBookDTO userBookDTO);
}
