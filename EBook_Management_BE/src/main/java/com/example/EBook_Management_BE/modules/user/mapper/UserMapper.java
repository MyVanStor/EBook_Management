package com.example.EBook_Management_BE.modules.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.modules.user.dto.UserDTO;
import com.example.EBook_Management_BE.modules.user.response.UserResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
	UserMapper iNSTANCE = Mappers.getMapper(UserMapper.class);
	
	User mapToUserEntity(UserDTO userDTO);
	
	UserResponse mapToUserResponse(User user);
}
