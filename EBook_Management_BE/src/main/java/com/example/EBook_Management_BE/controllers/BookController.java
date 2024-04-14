package com.example.EBook_Management_BE.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.example.EBook_Management_BE.dtos.BookDTO;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.enums.StatusUserBook;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.BookMapper;
import com.example.EBook_Management_BE.responses.BookResponse;
import com.example.EBook_Management_BE.services.author.IAuthorRedisService;
import com.example.EBook_Management_BE.services.author.IAuthorService;
import com.example.EBook_Management_BE.services.book.IBookRedisService;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.category.ICategoryRedisService;
import com.example.EBook_Management_BE.services.category.ICategoryService;
import com.example.EBook_Management_BE.services.painter.IPainterRedisService;
import com.example.EBook_Management_BE.services.painter.IPainterService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.services.userbook.IUserBookRedisService;
import com.example.EBook_Management_BE.services.userbook.IUserBookService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.BOOK)
@RequiredArgsConstructor
public class BookController {
	private final IBookService bookService;
	private final IBookRedisService bookRedisService;
	
	private final IUserBookService userBookService;
	private final IUserBookRedisService userBookRedisService;
	
	private final IUserRedisService userRedisService;
	private final IUserService userService;
	
	private final ICategoryRedisService categoryRedisService;
	private final ICategoryService categoryService;
	private final IPainterRedisService painterRedisService;
	private final IPainterService painterService;
	private final IAuthorRedisService authorRedisService;
	private final IAuthorService authorService;

	private final LocalizationUtils localizationUtils;

	@Autowired
	private BookMapper bookMapper;

	@PostMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SYS-ADMIN')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createBook(@PathVariable Long userId, @Valid @RequestBody BookDTO bookDTO) throws Exception {
		Book book = bookMapper.mapToBookEntity(bookDTO);
		
		Set<Category> categories = new HashSet<>();
		for (Long categoryId : bookDTO.getCategoryIds()) {
			Category category = categoryRedisService.getCategoryById(categoryId);
			if (category == null) {
				category = categoryService.getCategoryById(categoryId);
				
				categoryRedisService.saveCategoryById(categoryId, category);
			}
			category.setBooks(Set.of(book));
			categories.add(category);
		}
		
		Set<Painter> painters = new HashSet<>();
		for (Long painterId : bookDTO.getPainterIds()) {
			Painter painter = painterRedisService.getPainterById(painterId);
			if (painter == null) {
				painter = painterService.getPainterById(painterId);
				
				painterRedisService.savePainterById(painterId, painter);
			}
			painter.setBooks(Set.of(book));
			painters.add(painter);
		}
		
		Set<Author> authors = new HashSet<>();
		for (Long authorId : bookDTO.getAuthorIds()) {
			Author author = authorRedisService.getAuthorById(authorId);
			if (author == null) {
				author = authorService.getAuthorById(authorId);
				
				authorRedisService.saveAuthorById(authorId, author);
			}
			author.setBooks(Set.of(book));
			authors.add(author);
		}
		book.setAuthors(authors);
		book.setCategories(categories);
		book.setPainters(painters);
		
		User user = userRedisService.getUserById(userId);
		if (user == null) {
			user = userService.getUserById(userId);
			
			userRedisService.saveUserById(user.getId(), user);
		}
		
		Book newBook = bookService.createBook(book);
		bookRedisService.saveBookById(newBook.getId(), newBook);
		
		UserBook userBook = UserBook.builder()
				.status(StatusUserBook.OWNER)
				.book(newBook)
				.user(user)
				.build();
		UserBook newUserBook = userBookService.createUserBook(userBook);
		userBookRedisService.saveUserBookById(newBook.getId(), newUserBook);

		BookResponse bookResponse = bookMapper.mapToBookResponse(newBook);
		bookResponse.setUserBooks(Set.of(newUserBook));
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.data(bookResponse)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.BOOK_CREATE_SUCCESSFULLY))
				.build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getBookById(@PathVariable Long id) throws Exception {
		Book existingBook = bookRedisService.getBookById(id);
		if (existingBook == null) {
			existingBook = bookService.getBookById(id);
			
			bookRedisService.saveBookById(id, existingBook);
		}
		
		BookResponse bookResponse = bookMapper.mapToBookResponse(existingBook);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.BOOK_GET_BY_ID_SUCCESSFULLY))
				.data(bookResponse)
				.build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) throws Exception {
		Book book = bookMapper.mapToBookEntity(bookDTO);
		
		Set<Category> categories = new HashSet<>();
		for (Long categoryId : bookDTO.getCategoryIds()) {
			Category category = categoryRedisService.getCategoryById(categoryId);
			if (category == null) {
				category = categoryService.getCategoryById(categoryId);
				
				categoryRedisService.saveCategoryById(categoryId, category);
			}
			category.setBooks(Set.of(book));
			categories.add(category);
		}
		
		Set<Painter> painters = new HashSet<>();
		for (Long painterId : bookDTO.getPainterIds()) {
			Painter painter = painterRedisService.getPainterById(painterId);
			if (painter == null) {
				painter = painterService.getPainterById(painterId);
				
				painterRedisService.savePainterById(painterId, painter);
			}
			painter.setBooks(Set.of(book));
			painters.add(painter);
		}
		
		Set<Author> authors = new HashSet<>();
		for (Long authorId : bookDTO.getAuthorIds()) {
			Author author = authorRedisService.getAuthorById(authorId);
			if (author == null) {
				author = authorService.getAuthorById(authorId);
				
				authorRedisService.saveAuthorById(authorId, author);
			}
			author.setBooks(Set.of(book));
			authors.add(author);
		}
		book = bookService.updateBook(id, book);
		bookRedisService.saveBookById(id, book);
		
		BookResponse bookResponse = bookMapper.mapToBookResponse(book);

		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.BOOK_UPDATE_SUCCESSFULLY))
				.data(bookResponse)
				.build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject> deleteBook(@PathVariable Long id) throws Exception {
		bookService.deleteBook(id);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.BOOK_DELETE_SUCCESSFULLY))
				.build());
	}
}
