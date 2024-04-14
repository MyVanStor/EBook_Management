package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.CategoryDTO;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.CategoryMapper;
import com.example.EBook_Management_BE.responses.CategoryResponse;
import com.example.EBook_Management_BE.services.category.ICategoryRedisService;
import com.example.EBook_Management_BE.services.category.ICategoryService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.CATEGORY)
@Validated
@RequiredArgsConstructor
public class CategoryController {
	private final ICategoryService categoryService;
	private final ICategoryRedisService categoryRedisService;
	
	private final LocalizationUtils localizationUtils;

	@Autowired
	private CategoryMapper categoryMapper;
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Long id) throws Exception {
		Category existingCategory = categoryRedisService.getCategoryById(id);
		if (existingCategory == null) {
			existingCategory = categoryService.getCategoryById(id);
			
			categoryRedisService.saveCategoryById(id, existingCategory);
		}
		
		CategoryResponse categoryResponse = categoryMapper.mapToCategoryResponse(existingCategory);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_GET_BY_ID_SUCCESSFULLY))
				.data(categoryResponse)
				.build());
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SYS-ADMIN')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
		Category category = categoryMapper.mapToCategoryEntity(categoryDTO);
		
		Category newCategory = categoryService.createCategory(category);
		categoryRedisService.saveCategoryById(newCategory.getId(), newCategory);
		
		CategoryResponse categoryResponse = categoryMapper.mapToCategoryResponse(newCategory);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_CREATE_SUCCESSFULLY))
				.data(categoryResponse)
				.build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id,
			@Valid @RequestBody CategoryDTO categoryDTO) throws Exception {
		Category category = categoryMapper.mapToCategoryEntity(categoryDTO);
		
		categoryService.updateCategory(id, category);
		categoryRedisService.saveCategoryById(id, category);
		
		CategoryResponse categoryResponse = categoryMapper.mapToCategoryResponse(category);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_UPDATE_SUCCESSFULLY))
				.data(categoryResponse)
				.build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) throws Exception {
		categoryService.deleteCategoryById(id);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_DELETE_SUCCESSFULLY))
						.build());
	}
}
