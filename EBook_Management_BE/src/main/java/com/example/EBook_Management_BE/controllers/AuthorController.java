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
import com.example.EBook_Management_BE.dtos.AuthorDTO;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.AuthorMapper;
import com.example.EBook_Management_BE.responses.AuthorResponse;
import com.example.EBook_Management_BE.services.author.IAuthorRedisService;
import com.example.EBook_Management_BE.services.author.IAuthorService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.AUTHOR)
@Validated
@RequiredArgsConstructor
public class AuthorController {
	private final IAuthorService authorService;
	private final IAuthorRedisService authorRedisService;
	private final IUserService userService;
	private final IUserRedisService userRedisService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private AuthorMapper authorMapper;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getAuthorById(@PathVariable Long id) throws Exception {
		Author existingAuthor = authorRedisService.getAuthorById(id);
		if (existingAuthor == null) {
			existingAuthor = authorService.getAuthorById(id);
			
			authorRedisService.saveAuthorById(id, existingAuthor);
		}
		
		AuthorResponse authorResponse = authorMapper.mapToAuthorResponse(existingAuthor);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_GET_BY_ID_SUCCESSFULLY))
				.data(authorResponse)
				.build());
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SYS-ADMIN')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) throws Exception {
		User user = userRedisService.getUserById(authorDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(authorDTO.getUserId());
			
			userRedisService.saveUserById(user.getId(), user);
		}
		
		Author author = authorMapper.mapToAuthorEntity(authorDTO);
		author.setUser(user);
		
		Author newAuthor = authorService.createAuthor(author);
		authorRedisService.saveAuthorById(newAuthor.getId(), newAuthor);
		
		AuthorResponse authorResponse = authorMapper.mapToAuthorResponse(newAuthor);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_CREATE_SUCCESSFULLY))
				.data(authorResponse)
				.build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> updateAuthor(@PathVariable Long id,
			@Valid @RequestBody AuthorDTO authorDTO) throws Exception {
		User user = userRedisService.getUserById(authorDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(authorDTO.getUserId());
			
			userRedisService.saveUserById(user.getId(), user);
		}
		
		Author author = authorMapper.mapToAuthorEntity(authorDTO);
		author.setUser(user);
		
		authorService.updateAuthor(id, author);
		authorRedisService.saveAuthorById(id, author);
		
		AuthorResponse authorResponse = authorMapper.mapToAuthorResponse(author);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_UPDATE_SUCCESSFULLY))
				.data(authorResponse)
				.build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYS-ADMIN')")
	public ResponseEntity<ResponseObject> deleteAuthor(@PathVariable Long id) throws Exception {
		authorService.deleteAuthorById(id);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.AUTHOR_DELETE_SUCCESSFULLY))
						.build());
	}
}
