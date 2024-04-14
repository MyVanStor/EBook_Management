package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.entity.Book;

public interface IBookRedisService {
	void clearById(Long id);

	Book getBookById(Long bookId) throws Exception;

	void saveBookById(Long bookId, Book book) throws Exception;
}
