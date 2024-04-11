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
import com.example.EBook_Management_BE.dtos.AuthorDTO;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.responses.AuthorResponse;
import com.example.EBook_Management_BE.services.author.AuthorService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.AUTHOR)
@Validated
@RequiredArgsConstructor
public class AuthorController {
	private final AuthorService authorService;
	private final LocalizationUtils localizationUtils;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getAuthorById(@PathVariable Long id) {
		Author existingAuthor = authorService.getAuthorById(id);
		return ResponseEntity.ok(ResponseObject.builder().data(existingAuthor)
				.message("Get author information successfully").status(HttpStatus.OK).build());
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorDTO authorDTO, BindingResult result) {
		AuthorResponse authorResponse = new AuthorResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			authorResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_AUTHOR_SUCCESSFULLY));
			authorResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(authorResponse);
		}

		Author author = authorService.createAuthor(authorDTO);
		authorResponse.setAuthor(author);
		return ResponseEntity.created(null).body(authorResponse);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> updateAuthor(@PathVariable Long id,
			@Valid @RequestBody AuthorDTO authorDTO) {
		authorService.updateAuthor(id, authorDTO);
		return ResponseEntity.ok(ResponseObject.builder().data(authorService.getAuthorById(id))
				.message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_AUTHOR_SUCCESSFULLY)).build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> deleteAuthor(@PathVariable Long id) throws Exception {
		authorService.deleteAuthorById(id);
		return ResponseEntity
				.ok(ResponseObject.builder().status(HttpStatus.OK).message("Delete author successfully").build());
	}
}
