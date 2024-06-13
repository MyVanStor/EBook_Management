package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IBookService {
	Book createBook(Book book) throws Exception;

	Book getBookById(Long bookId) throws Exception;

	Book updateBook(Long bookId, Book book) throws Exception;

	void deleteBook(Long bookId) throws Exception;

	List<Book> getAllBookByUser(User user) throws JsonProcessingException;
}
