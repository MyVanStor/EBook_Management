package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;

import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.CategoryDTO;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.responses.CategoryResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	Category mapToCategoryEntity(CategoryDTO categoryDTO);

	Category mapToCategoryEntity(CategoryResponse categoryResponse);

	CategoryResponse mapToCategoryResponse(Category category);
}
