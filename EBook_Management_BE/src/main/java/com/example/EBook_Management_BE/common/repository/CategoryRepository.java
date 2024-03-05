package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
	Set<Category> findByBooks(Set<Book> books);
}
