package com.example.EBook_Management_BE.modules.category.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Category;
import com.example.EBook_Management_BE.common.repository.BookRepository;
import com.example.EBook_Management_BE.common.repository.CategoryRepository;
import com.example.EBook_Management_BE.modules.category.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
	private final CategoryRepository categoryRepository;
	private final BookRepository bookRepository;

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

	@Override
	@Transactional
	public Category deleteCategoryById(Long categoryId) throws Exception {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ChangeSetPersister.NotFoundException());
		Set<Category> categories = new HashSet<Category>();
		categories.add(category);
		
		List<Book> books = bookRepository.findByCategories(categories);
		if (!books.isEmpty()) {
			throw new IllegalStateException("Cannot delete category with associated books");
		} else {
			categoryRepository.deleteById(categoryId);
			return category;
		}
	}

}
