package com.example.EBook_Management_BE.modules.author.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Author;
import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.repository.AuthorRepository;
import com.example.EBook_Management_BE.common.repository.BookRepository;
import com.example.EBook_Management_BE.modules.author.dto.AuthorDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional
	public Author createAuthor(AuthorDTO authorDTO) {
		Author newAuthor = Author.builder().name(authorDTO.getName()).build();

		return authorRepository.save(newAuthor);
	}

	@Override
	public Author getAuthorById(Long authorId) {
		return authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author cannot found"));
	}

	@Override
	@Transactional
	public Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
		Author exitstingAuthor = getAuthorById(authorId);

		exitstingAuthor.setName(authorDTO.getName());
		authorRepository.save(exitstingAuthor);

		return exitstingAuthor;
	}

	@Override
	@Transactional
	public Author deleteAuthorById(Long authorId) throws Exception {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new ChangeSetPersister.NotFoundException());
		Set<Author> authors = new HashSet<Author>();
		authors.add(author);
		
		List<Book> books = bookRepository.findByAuthors(authors);
		if (!books.isEmpty()) {
			throw new IllegalStateException("Cannot delete author with associated books");
		} else {
			authorRepository.deleteById(authorId);
			return author;
		}
	}

}
