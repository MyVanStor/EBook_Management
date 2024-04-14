package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
	boolean existsByName(String name);
	
	Set<Category> findByBooks(Set<Book> books);
}
