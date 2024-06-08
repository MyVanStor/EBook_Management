package com.example.EBook_Management_BE.services.category;

import com.example.EBook_Management_BE.entity.Category;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ICategoryRedisService {
	void clearById(Long id);

	Category getCategoryById(Long categoryId) throws Exception;

	void saveCategoryById(Long categoryId, Category category) throws Exception;

	List<Category> getAllCategory() throws JsonProcessingException;

	void saveAllCategory(List<Category> categories) throws JsonProcessingException;
}
