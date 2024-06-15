package com.example.EBook_Management_BE.services.category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.exceptions.DeleteException;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.repositories.book.BookRepository;
import com.example.EBook_Management_BE.repositories.CategoryRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
	private final CategoryRepository categoryRepository;
	private final ICategoryRedisService categoryRedisService;
	private final BookRepository bookRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Category createCategory(Category category) throws Exception {
		if (categoryRepository.existsByName(category.getName())) {
			throw new DuplicateException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.CATEGORY_DUPLICATE_CATEGORY));
		}

		categoryRepository.save(category);
		categoryRedisService.saveCategoryById(category.getId(), category);

		return categoryRepository.save(category);
	}

	@Override
	public Category getCategoryById(Long categoryId) throws Exception {
		Category category = categoryRedisService.getCategoryById(categoryId);
		if (category == null) {
			category = categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.CATEGORY_NOT_FOUND)));
			
			categoryRedisService.saveCategoryById(categoryId, category);
		}
		return category;
	}

	@Override
	@Transactional
	public Category updateCategory(Long categoryId, Category categoryUpdate) throws Exception {
		Category exitstingCategory = getCategoryById(categoryId);
		if (exitstingCategory.getName().equals(categoryUpdate.getName())) {
			throw new DuplicateException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.CATEGORY_DUPLICATE_CATEGORY));
		}

		categoryUpdate.setId(exitstingCategory.getId());
		categoryUpdate = categoryRepository.save(categoryUpdate);

		return categoryUpdate;
	}

	@Override
	@Transactional
	public void deleteCategoryById(Long categoryId) throws Exception {
		Category category = getCategoryById(categoryId);
		Set<Category> categories = new HashSet<Category>();
		categories.add(category);

		if (bookRepository.existsByCategories(categories)) {
			throw new DeleteException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.CATEGORY_DELETE_HAVE_ASSOCIATED_BOOK));
		} else {
			categoryRepository.deleteById(categoryId);
		}
	}

	@Override
	public List<Category> getAllCategory() throws JsonProcessingException {
		List<Category> categories = categoryRedisService.getAllCategory();
		if (categories == null) {
			categories = categoryRepository.findAll();

			categoryRedisService.saveAllCategory(categories);
		}

		return categories;
	}

}
