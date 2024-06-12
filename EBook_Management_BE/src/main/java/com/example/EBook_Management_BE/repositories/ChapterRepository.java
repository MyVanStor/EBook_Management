package com.example.EBook_Management_BE.repositories;

import com.example.EBook_Management_BE.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Chapter;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByBook(Book book);
}
