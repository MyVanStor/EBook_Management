package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.dtos.book.SearchBookDTO;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.repositories.book.BookRepositoryCustom;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.constants.StatusUserBook;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.book.BookRepository;
import com.example.EBook_Management_BE.repositories.UserBookRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
	private final BookRepository bookRepository;
	private final IBookRedisService bookRedisService;
	private final BookRepositoryCustom bookRepositoryCustom;
	private final UserBookRepository userBookRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Book createBook(Book book) throws Exception {
		bookRepository.save(book);
		
		userBookRepository.save(book.getUserBooks().iterator().next());
		
		bookRedisService.saveBookById(book.getId(), book);
		
		return book;
	}

	@Override
	public Book getBookById(Long bookId) throws Exception {
		Book book = bookRedisService.getBookById(bookId);
		if (book == null) {
			book = bookRepository.findById(bookId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.BOOK_NOT_FOUND)));
			
			bookRedisService.saveBookById(bookId, book);
		}
		
		return book;
	}

	@Override
	@Transactional
	public Book updateBook(Long bookId, Book book) throws Exception {
		Book existingBook = getBookById(bookId);

		book.setId(existingBook.getId());
		bookRepository.save(book);

		return book;
	}

	@Override
	@Transactional
	public void deleteBook(Long bookId) throws Exception {
		Book book = getBookById(bookId);

		for (UserBook userBook : book.getUserBooks()) {
			if (Objects.equals(userBook.getStatus(), StatusUserBook.BUYER)) {
				throw new IllegalStateException(
						localizationUtils.getLocalizedMessage(MessageExceptionKeys.BOOK_DELETE_HAVE_USER_BUYING));
			}
		}

		userBookRepository.deleteAll(book.getUserBooks());

		bookRepository.deleteById(bookId);
	}

	@Override
	public List<Book> getAllBookByUser(User user) throws JsonProcessingException {
		List<Book> books = bookRedisService.getAllBookByUser(user);
		if (books == null) {
			books = bookRepository.findAllByUserId(user.getId());

			bookRedisService.saveAllBookByUser(books, user);
		}

		return books;
	}

	@Override
	public List<Book> getAllBookByType(String type, Long page, Long limit) throws JsonProcessingException {
		Pageable pageable = PageRequest.of(Math.toIntExact(page), Math.toIntExact(limit));
		Page<Book> bookPage = bookRepository.findAllByType(type, pageable);
		return bookPage.getContent();
	}

	@Override
	public Page<Book> searchBook(SearchBookDTO searchBookDTO, Long page, Long limit) {
		Pageable pageable = PageRequest.of(Math.toIntExact(page), Math.toIntExact(limit));
		Page<Book> bookPage = bookRepositoryCustom.searchBook(searchBookDTO, pageable);

		return  bookPage;
	}

	@Override
	public List<Book> getBookByAccount(User user) {
		List<Book> books = bookRepository.findAllBooksByAccount(user.getId());

		return books;
	}
}
