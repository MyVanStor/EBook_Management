package com.example.EBook_Management_BE.services.category;

import com.example.EBook_Management_BE.dtos.CategoryDTO;
import com.example.EBook_Management_BE.entity.Category;

public interface ICategoryService {
	Category createCategory(CategoryDTO categoryDTO);
	
	Category getCategoryById(Long categoryId);
	
	Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
	
	Category deleteCategoryById(Long categoryId) throws Exception;
}
