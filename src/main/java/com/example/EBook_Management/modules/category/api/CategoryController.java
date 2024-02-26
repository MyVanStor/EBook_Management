package com.example.EBook_Management.modules.category.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management.common.components.LocalizationUtils;
import com.example.EBook_Management.common.entity.Category;
import com.example.EBook_Management.common.enums.Uri;
import com.example.EBook_Management.common.utils.MessageKeys;
import com.example.EBook_Management.modules.category.dto.CategoryDTO;
import com.example.EBook_Management.modules.category.response.CategoryResponse;
import com.example.EBook_Management.modules.category.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.CATEGORY)
@Validated
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	private final LocalizationUtils localizationUtils;
	
	@PostMapping("")
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
		System.out.println(category);
		categoryResponse.setCategory(category);
		return ResponseEntity.ok(categoryResponse);
	}
}
