package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.RoleDTO;
import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.responses.RoleResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
	RoleMapper iNSTANCE = Mappers.getMapper(RoleMapper.class);

	Role mapToRoleEntity(RoleDTO roleDTO);

	Role mapToRoleEntity(RoleResponse roleResponse);

	RoleResponse mapToRoleResponse(Role role);
}
