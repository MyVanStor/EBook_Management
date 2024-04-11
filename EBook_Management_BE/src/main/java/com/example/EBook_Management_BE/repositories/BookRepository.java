package com.example.EBook_Management_BE.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.entity.Painter;

public interface BookRepository extends JpaRepository<Book, Long> {
	boolean existsByTitle(String title);

	Page<Book> findAll(Pageable pageable);

	List<Book> findByCategories(Set<Category> categories);

	List<Book> findByPainters(Set<Painter> painters);

	List<Book> findByAuthors(Set<Author> authors);

}
