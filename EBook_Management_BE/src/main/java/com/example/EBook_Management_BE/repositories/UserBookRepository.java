package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.entity.UserBook;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
	Set<UserBook> findByBookAndUser(Book book, User user);
	
	boolean existsByUserAndBook(User user, Book book);
}
