package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.CategoryDTO;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.responses.CategoryResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	Category mapToCategoryEntity(CategoryDTO categoryDTO);

	Category mapToCategoryEntity(CategoryResponse categoryResponse);

	CategoryResponse mapToCategoryResponse(Category category);
}
