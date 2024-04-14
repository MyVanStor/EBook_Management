package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface IBookService {
	Book createBook(Book book) throws Exception;

	Book getBookById(Long bookId) throws DataNotFoundException;

	Book updateBook(Long bookId, Book book) throws Exception;

	void deleteBook(Long bookId) throws Exception;

}
