package com.example.EBook_Management_BE.services.author;

import com.example.EBook_Management_BE.entity.Author;

public interface IAuthorService {
	Author createAuthor(Author author) throws Exception;
	
	Author getAuthorById(Long authorId) throws Exception;

	Author updateAuthor(Long authorId, Author authorUpdate) throws Exception;

	void deleteAuthorById(Long authorId) throws Exception;

}
