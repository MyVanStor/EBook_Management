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
	Page<Book> findAll(Pageable pageable);

	boolean existsByCategories(Set<Category> categories);

	@Query(value = """
            SELECT b.*
            FROM books b
            JOIN user_book ub ON b.id = ub.book_id
            JOIN users u ON ub.user_id = u.id
            WHERE u.id = :userId""", nativeQuery = true)
	List<Book> findAllByUserId(@Param("userId") Long userId);

	@Query(value = """
            SELECT b 
            FROM Book b 
            WHERE b.typeOfBook = :type
            ORDER BY b.createdAt DESC
            """)
	Page<Book> findAllByType(@Param("type") String type, Pageable pageable);
}
