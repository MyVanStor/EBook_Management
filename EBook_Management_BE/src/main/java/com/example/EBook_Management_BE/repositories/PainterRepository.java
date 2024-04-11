package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Painter;

public interface PainterRepository extends JpaRepository<Painter, Long>{
	Set<Painter> findByBooks(Set<Book> books);
}
