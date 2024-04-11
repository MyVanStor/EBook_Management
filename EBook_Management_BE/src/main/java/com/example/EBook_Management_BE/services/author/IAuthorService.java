package com.example.EBook_Management_BE.services.author;

import com.example.EBook_Management_BE.dtos.AuthorDTO;
import com.example.EBook_Management_BE.entity.Author;

public interface IAuthorService {
	Author createAuthor(AuthorDTO authorDTO);
	
	Author getAuthorById(Long authorId);

	Author updateAuthor(Long authorId, AuthorDTO authorDTO);

	Author deleteAuthorById(Long authorId) throws Exception;

}
