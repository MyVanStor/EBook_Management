package com.example.EBook_Management_BE.services.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.enums.StatusUserBook;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.BookRepository;
import com.example.EBook_Management_BE.repositories.UserBookRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
	private final BookRepository bookRepository;
	private final UserBookRepository userBookRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Book createBook(Book book) throws Exception {
		return bookRepository.save(book);
	}

	@Override
	public Book getBookById(Long bookId) throws DataNotFoundException {
		return bookRepository.findById(bookId).orElseThrow(() -> new DataNotFoundException(
				localizationUtils.getLocalizedMessage(MessageExceptionKeys.BOOK_NOT_FOUND)));
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
			if (userBook.getStatus() == StatusUserBook.BUYER) {
				throw new IllegalStateException(
						localizationUtils.getLocalizedMessage(MessageExceptionKeys.BOOK_DELETE_HAVE_USER_BUYING));
			}
		}

		userBookRepository.deleteAll(book.getUserBooks());

		bookRepository.deleteById(bookId);
	}
}
