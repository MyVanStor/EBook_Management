package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.RoleDTO;
import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.responses.RoleResponse;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	Role mapToRoleEntity(RoleDTO roleDTO);

	Role mapToRoleEntity(RoleResponse roleResponse);

	RoleResponse mapToRoleResponse(Role role);
}
