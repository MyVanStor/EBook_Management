package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public interface IBookRedisService {
	void clear(Book book);

	Book getBookById(Long bookId) throws Exception;

	void saveBookById(Long bookId, Book book) throws Exception;

	List<Book> getAllBookByUser(User user) throws JsonProcessingException;

	void saveAllBookByUser(List<Book> books, User user) throws JsonProcessingException;
}
