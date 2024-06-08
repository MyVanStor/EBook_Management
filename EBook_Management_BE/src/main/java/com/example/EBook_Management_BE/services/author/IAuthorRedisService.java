package com.example.EBook_Management_BE.services.author;

import com.example.EBook_Management_BE.entity.Author;

import java.util.List;

public interface IAuthorRedisService {
	void clearById(Long id);
	
	Author getAuthorById(Long authorId) throws Exception;
	
	void saveAuthorById(Long authorId, Author author) throws Exception;

	List<Author> getAllAuthors() throws Exception;

	void saveAllAuthors(List<Author> authors) throws Exception;
}
