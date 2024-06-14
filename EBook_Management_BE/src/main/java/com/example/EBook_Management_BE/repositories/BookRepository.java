package com.example.EBook_Management_BE.repositories;

import java.util.List;
import java.util.Set;

import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.entity.UserBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
	boolean existsByTitle(String title);

	Page<Book> findAll(Pageable pageable);

	List<Book> findByCategories(Set<Category> categories);

	boolean existsByCategories(Set<Category> categories);

	@Query(value = """
            SELECT b.*
            FROM books b
            JOIN user_book ub ON b.id = ub.book_id
            JOIN users u ON ub.user_id = u.id
            WHERE u.id = :userId""", nativeQuery = true)
	List<Book> findAllByUserId(@Param("userId") Long userId);
}
