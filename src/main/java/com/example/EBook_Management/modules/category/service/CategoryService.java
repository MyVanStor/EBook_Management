package com.example.EBook_Management.modules.category.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management.common.entity.Category;
import com.example.EBook_Management.common.repository.CategoryRepository;
import com.example.EBook_Management.modules.category.dto.CategoryDTO;

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

}
