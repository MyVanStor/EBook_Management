package com.example.EBook_Management.modules.category.service;

import com.example.EBook_Management.common.entity.Category;
import com.example.EBook_Management.modules.category.dto.CategoryDTO;

public interface ICategoryService {
	Category createCategory(CategoryDTO categoryDTO);
}
