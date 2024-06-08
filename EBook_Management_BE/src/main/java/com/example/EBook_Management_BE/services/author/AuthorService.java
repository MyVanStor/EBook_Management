package com.example.EBook_Management_BE.services.author;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.EBook_Management_BE.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (exitstingAuthor.getName().equals(authorUpdate.getName()) && exitstingAuthor.getUser().equals(authorUpdate.getUser())) {
            throw new DuplicateException(
                    localizationUtils.getLocalizedMessage(MessageExceptionKeys.AUTHOR_DUPLICATE_AUTHOR));
        }

        if (!exitstingAuthor.getUser().getId().equals(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            throw new AccessDeniedException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.DOES_NOT_HAVE_PERMISSION));
        }

        authorUpdate.setId(exitstingAuthor.getId());
        authorRepository.save(authorUpdate);

        return authorUpdate;
    }

    @Override
    @Transactional
    public void deleteAuthorById(Long authorId) throws Exception {
        Author exitstingAuthor = getAuthorById(authorId);
        Set<Author> authors = new HashSet<Author>();
        authors.add(exitstingAuthor);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
            if (!exitstingAuthor.getUser().getId().equals(((User) authentication.getPrincipal()).getId())) {
                throw new AccessDeniedException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.DOES_NOT_HAVE_PERMISSION));
            }
        }

        if (bookRepository.existsByAuthors(authors)) {
            throw new DeleteException(
                    localizationUtils.getLocalizedMessage(MessageExceptionKeys.AUTHOR_DELETE_HAVE_ASSOCIATED_BOOK));
        } else {
            authorRepository.deleteById(authorId);
        }
    }

    @Override
    public List<Author> getAllAuthors() throws Exception {
        List<Author> authors = authorRedisService.getAllAuthors();
        if (authors == null) {
            authors = authorRepository.findAll();

            authorRedisService.saveAllAuthors(authors);
        }

        return authors;
    }

}
