package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.dtos.BookDTO;
import com.example.EBook_Management_BE.entity.Book;

public interface IBookService {
	Book createBook(BookDTO bookDTO) throws Exception;

	Book getBookById(Long bookId);

	Book updateBook(Long bookId, BookDTO bookDTO);

	void deleteBook(Long bookId) throws Exception;

}
