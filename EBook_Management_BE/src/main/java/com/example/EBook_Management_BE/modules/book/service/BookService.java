package com.example.EBook_Management_BE.modules.book.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Author;
import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Category;
import com.example.EBook_Management_BE.common.entity.Painter;
import com.example.EBook_Management_BE.common.entity.UserBook;
import com.example.EBook_Management_BE.common.repository.AuthorRepository;
import com.example.EBook_Management_BE.common.repository.BookRepository;
import com.example.EBook_Management_BE.common.repository.CategoryRepository;
import com.example.EBook_Management_BE.common.repository.PainterRepository;
import com.example.EBook_Management_BE.common.repository.UserBookRepository;
import com.example.EBook_Management_BE.modules.book.dto.BookDTO;
import com.example.EBook_Management_BE.modules.book.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final PainterRepository painterRepository;
	private final AuthorRepository authorRepository;
	private final UserBookRepository userBookRepository;

	@Autowired
	private BookMapper bookMapper;

	@Override
	@Transactional
	public Book createBook(BookDTO bookDTO) throws Exception {
		Book book = bookMapper.mapToBookEntity(bookDTO);

		Set<Category> categories = new HashSet<>();
		for (Long categoryId : bookDTO.getCategoryIds()) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			category.setBooks(Set.of(book));
			categories.add(category);
		}

		Set<Painter> painters = new HashSet<>();
		for (Long painterId : bookDTO.getPainterIds()) {
			Painter painter = painterRepository.findById(painterId)
					.orElseThrow(() -> new RuntimeException("Painter not found"));
			painter.setBooks(Set.of(book));
			painters.add(painter);
		}

		Set<Author> authors = new HashSet<>();
		for (Long authorId : bookDTO.getAuthorIds()) {
			Author author = authorRepository.findById(authorId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			author.setBooks(Set.of(book));
			authors.add(author);
		}

		book.setAuthors(authors);
		book.setCategories(categories);
		book.setPainters(painters);

		return bookRepository.save(book);
	}

	@Override
	public Book getBookById(Long bookId) {
		return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
	}

	@Override
	@Transactional
	public Book updateBook(Long bookId, BookDTO bookDTO) {
		Book existingBook = getBookById(bookId);
		existingBook.getAuthors().clear();
		existingBook.getCategories().clear();
		existingBook.getPainters().clear();

		for (Long categoryId : bookDTO.getCategoryIds()) {
			Category category = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			category.setBooks(Set.of(existingBook));
			existingBook.getCategories().add(category);
		}

		for (Long painterId : bookDTO.getPainterIds()) {
			Painter painter = painterRepository.findById(painterId)
					.orElseThrow(() -> new RuntimeException("Painter not found"));
			painter.setBooks(Set.of(existingBook));
			existingBook.getPainters().add(painter);
		}

		for (Long authorId : bookDTO.getAuthorIds()) {
			Author author = authorRepository.findById(authorId)
					.orElseThrow(() -> new RuntimeException("Author not found"));
			author.setBooks(Set.of(existingBook));
			existingBook.getAuthors().add(author);
		}

		return bookRepository.save(existingBook);
	}

	@Override
	@Transactional
	public void deleteBook(Long bookId) throws Exception{
		Book book = getBookById(bookId);
		
		for (UserBook userBook : book.getUserBooks()) {
			if(userBook.getStatus() == "buyer") {
				throw new IllegalStateException("Cannot delete book because have user buy book");
			}
		}
		
		userBookRepository.deleteAll(book.getUserBooks());
		
		bookRepository.deleteById(bookId);
	}
}
