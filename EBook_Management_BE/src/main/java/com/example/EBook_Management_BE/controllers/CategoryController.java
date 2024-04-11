package com.example.EBook_Management_BE.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.CategoryDTO;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.responses.CategoryResponse;
import com.example.EBook_Management_BE.services.category.CategoryService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.CATEGORY)
@Validated
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	private final LocalizationUtils localizationUtils;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getCategoryById(@PathVariable Long id) {
		Category existingCategory = categoryService.getCategoryById(id);
		return ResponseEntity.ok(ResponseObject.builder().data(existingCategory)
				.message("Get category information successfully").status(HttpStatus.OK).build());
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			BindingResult result) {
		CategoryResponse categoryResponse = new CategoryResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			categoryResponse
					.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
			categoryResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(categoryResponse);
		}

		Category category = categoryService.createCategory(categoryDTO);
		categoryResponse.setCategory(category);
		return ResponseEntity.created(null).body(categoryResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id,
			@Valid @RequestBody CategoryDTO categoryDTO) {
		categoryService.updateCategory(id, categoryDTO);
		return ResponseEntity.ok(ResponseObject.builder().data(categoryService.getCategoryById(id))
				.message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY)).build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) throws Exception {
		categoryService.deleteCategoryById(id);
		return ResponseEntity
				.ok(ResponseObject.builder().status(HttpStatus.OK).message("Delete category successfully").build());
	}
}
