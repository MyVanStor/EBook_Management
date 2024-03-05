package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Painter;

public interface PainterRepository extends JpaRepository<Painter, Long>{
	Set<Painter> findByBooks(Set<Book> books);
}
