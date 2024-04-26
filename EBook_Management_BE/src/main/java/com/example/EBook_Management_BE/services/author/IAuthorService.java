package com.example.EBook_Management_BE.services.author;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.exceptions.DuplicateException;

public interface IAuthorService {
	Author createAuthor(Author author) throws DuplicateException;
	
	Author getAuthorById(Long authorId) throws Exception;

	Author updateAuthor(Long authorId, Author authorUpdate) throws Exception;

	void deleteAuthorById(Long authorId) throws Exception;

}
