package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.UpdateUserDTO;
import com.example.EBook_Management_BE.dtos.UserDTO;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.responses.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	User mapToUserEntity(UserDTO userDTO);
	
	User mapToUserEntity(UpdateUserDTO updateUserDTO);
	
	UserResponse mapToUserResponse(User user);
}
