package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.entity.User;

public interface PainterRepository extends JpaRepository<Painter, Long>{
	boolean existsByNameAndUser(String name, User user);
	
	Set<Painter> findByBooks(Set<Book> books);
}
