package com.example.EBook_Management_BE.services.author;

import com.example.EBook_Management_BE.entity.Author;

public interface IAuthorRedisService {
	void clearById(Long id);
	
	Author getAuthorById(Long authorId) throws Exception;
	
	void saveAuthorById(Long authorId, Author author) throws Exception;
}
