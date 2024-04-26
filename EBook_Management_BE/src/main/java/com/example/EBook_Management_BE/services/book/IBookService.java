package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.entity.Book;

public interface IBookService {
	Book createBook(Book book) throws Exception;

	Book getBookById(Long bookId) throws Exception;

	Book updateBook(Long bookId, Book book) throws Exception;

	void deleteBook(Long bookId) throws Exception;

}
