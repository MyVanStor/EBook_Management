package com.example.EBook_Management_BE.services.category;

import com.example.EBook_Management_BE.entity.Category;

public interface ICategoryRedisService {
	void clearById(Long id);

	Category getCategoryById(Long categoryId) throws Exception;

	void saveCategoryById(Long categoryId, Category category) throws Exception;
}
