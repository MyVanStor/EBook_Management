package com.example.EBook_Management_BE.modules.author.service;

import com.example.EBook_Management_BE.common.entity.Author;
import com.example.EBook_Management_BE.modules.author.dto.AuthorDTO;

public interface IAuthorService {
	Author createAuthor(AuthorDTO authorDTO);
	
	Author getAuthorById(Long authorId);

	Author updateAuthor(Long authorId, AuthorDTO authorDTO);

	Author deleteAuthorById(Long authorId) throws Exception;

}
