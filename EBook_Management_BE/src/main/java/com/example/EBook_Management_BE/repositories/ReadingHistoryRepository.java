package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.entity.User;

public interface ReadingHistoryRepository extends JpaRepository<ReadingHistory, Long>{
	Set<ReadingHistory> findByUser(User user);

	boolean existsByBookAndUserAndChapter(Book book, User user, Chapter chapter);
}
