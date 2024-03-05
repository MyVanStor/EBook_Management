package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Author;
import com.example.EBook_Management_BE.common.entity.Book;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	Set<Author> findByBooks(Set<Book> books);
}
