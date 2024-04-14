package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.User;


public interface AuthorRepository extends JpaRepository<Author, Long> {
	boolean existsByNameAndUser(String name, User user);
	
	Set<Author> findByBooks(Set<Book> books);
}
