package com.example.EBook_Management_BE.modules.book.service;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.modules.book.dto.BookDTO;

public interface IBookService {
	Book createBook(BookDTO bookDTO) throws Exception;

	Book getBookById(Long bookId);

	Book updateBook(Long bookId, BookDTO bookDTO);

	void deleteBook(Long bookId) throws Exception;

}
