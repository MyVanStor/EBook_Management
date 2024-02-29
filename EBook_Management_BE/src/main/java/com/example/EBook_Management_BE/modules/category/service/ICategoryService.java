package com.example.EBook_Management_BE.modules.category.service;

import com.example.EBook_Management_BE.common.entity.Category;
import com.example.EBook_Management_BE.modules.category.dto.CategoryDTO;

public interface ICategoryService {
	Category createCategory(CategoryDTO categoryDTO);
	
	Category getCategoryById(Long categoryId);
	
	Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
