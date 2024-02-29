package com.example.EBook_Management_BE.modules.category.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Category;
import com.example.EBook_Management_BE.common.repository.CategoryRepository;
import com.example.EBook_Management_BE.modules.category.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
	private final CategoryRepository categoryRepository;

	@Override
	@Transactional
	public Category createCategory(CategoryDTO categoryDTO) {
		Category newCategory = Category.builder().name(categoryDTO.getName()).description(categoryDTO.getDescription())
				.build();

		return categoryRepository.save(newCategory);
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
	}

	@Override
	@Transactional
	public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) {
		Category existingCategory = getCategoryById(categoryId);
		
		existingCategory.setName(categoryDTO.getName());
		categoryRepository.save(existingCategory);
		
		return existingCategory;
	}

}
