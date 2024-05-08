package com.example.EBook_Management_BE.services.author;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.exceptions.DeleteException;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.repositories.AuthorRepository;
import com.example.EBook_Management_BE.repositories.BookRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
	private final AuthorRepository authorRepository;
	private final IAuthorRedisService authorRedisService;
	private final BookRepository bookRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Author createAuthor(Author author) throws Exception {
		if (authorRepository.existsByNameAndUser(author.getName(), author.getUser())) {
			throw new DuplicateException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.AUTHOR_DUPLICATE_AUTHOR));
		}
		
		authorRepository.save(author);
		authorRedisService.saveAuthorById(author.getId(), author);
		
		return author;
	}

	@Override
	public Author getAuthorById(Long authorId) throws Exception {
		Author author = authorRedisService.getAuthorById(authorId);
		if (author == null) {
			author = authorRepository.findById(authorId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.AUTHOR_NOT_FOUND)));
			
			authorRedisService.saveAuthorById(authorId, author);
		}

		return author;
	}

	@Override
	@Transactional
	public Author updateAuthor(Long authorId, Author authorUpdate) throws Exception {
		Author exitstingAuthor = getAuthorById(authorId);

		authorUpdate.setId(exitstingAuthor.getId());
		authorRepository.save(authorUpdate);
		
		authorRedisService.saveAuthorById(authorId, authorUpdate);
		
		return authorUpdate;
	}

	@Override
	@Transactional
	public void deleteAuthorById(Long authorId) throws Exception {
		Author author = getAuthorById(authorId);
		Set<Author> authors = new HashSet<Author>();
		authors.add(author);

		if (bookRepository.existsByAuthors(authors)) {
			throw new DeleteException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.AUTHOR_DELETE_HAVE_ASSOCIATED_BOOK));
		} else {
			authorRepository.deleteById(authorId);
		}
	}

}
