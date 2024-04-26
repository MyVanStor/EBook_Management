package com.example.EBook_Management_BE.services.category;

import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.exceptions.DuplicateException;

public interface ICategoryService {
	Category createCategory(Category category) throws DuplicateException;
	
	Category getCategoryById(Long categoryId) throws Exception;
	
	Category updateCategory(Long categoryId, Category category) throws Exception;
	
	void deleteCategoryById(Long categoryId) throws Exception;
}
