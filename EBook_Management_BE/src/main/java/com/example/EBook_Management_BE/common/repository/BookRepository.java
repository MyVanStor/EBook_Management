package com.example.EBook_Management_BE.common.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Author;
import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Category;
import com.example.EBook_Management_BE.common.entity.Painter;

public interface BookRepository extends JpaRepository<Book, Long> {
	boolean existsByTitle(String title);
	
	Page<Book> findAll(Pageable pageable);
	
	List<Book> findByCategories(Set<Category> categories);

	List<Book> findByPainters(Set<Painter> painters);

	List<Book> findByAuthors(Set<Author> authors);
}
