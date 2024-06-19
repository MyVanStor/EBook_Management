package com.example.EBook_Management_BE.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Rating;
import com.example.EBook_Management_BE.entity.User;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	Set<Rating> findByBook(Book book);

	boolean existsByUserAndBook(User user, Book book);
}
