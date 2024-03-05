package com.example.EBook_Management_BE.modules.book.api;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.UserBook;
import com.example.EBook_Management_BE.common.enums.StatusUserBook;
import com.example.EBook_Management_BE.common.enums.Uri;
import com.example.EBook_Management_BE.common.utils.ResponseObject;
import com.example.EBook_Management_BE.modules.book.dto.BookDTO;
import com.example.EBook_Management_BE.modules.book.mapper.BookMapper;
import com.example.EBook_Management_BE.modules.book.response.BookResponse;
import com.example.EBook_Management_BE.modules.book.service.BookService;
import com.example.EBook_Management_BE.modules.userbook.dto.UserBookDTO;
import com.example.EBook_Management_BE.modules.userbook.service.UserBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.BOOK)
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	private final UserBookService userBookService;

	@Autowired
	private BookMapper bookMapper;

	@PostMapping("/{userId}")
	public ResponseEntity<ResponseObject> createBook(@PathVariable Long userId, @Valid @RequestBody BookDTO bookDTO,
			BindingResult result) throws Exception {
		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
			return ResponseEntity.badRequest().body(ResponseObject.builder().message(String.join("; ", errorMessages))
					.status(HttpStatus.BAD_REQUEST).build());
		}

		Book newBook = bookService.createBook(bookDTO);

		UserBookDTO userBookDTO = UserBookDTO.builder().status(StatusUserBook.OWNER).bookId(newBook.getId())
				.userId(userId).build();
		UserBook userBook = userBookService.createUserBook(userBookDTO);

		BookResponse bookResponse = bookMapper.mapToBookResponse(newBook);
		bookResponse.setUserBooks(Set.of(userBook));
		
		return ResponseEntity.created(null).body(ResponseObject.builder().status(HttpStatus.CREATED).data(bookResponse)
				.message("Tạo sách mới thành công").build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getBookById(@PathVariable Long id) {
		Book existingBook = bookService.getBookById(id);

		return ResponseEntity.ok(ResponseObject.builder().data(existingBook)
				.message("Get book information successfully").status(HttpStatus.OK).build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
		bookService.updateBook(id, bookDTO);

		return ResponseEntity.ok(ResponseObject.builder().data(bookService.getBookById(id)).status(HttpStatus.OK)
				.message("Update ").build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject> deleteBook(@PathVariable Long id) throws Exception {
		bookService.deleteBook(id);
		return ResponseEntity.ok(ResponseObject.builder().data(null)
				.message(String.format("Book with id = %d deleted successfully", id)).status(HttpStatus.OK).build());
	}
}
