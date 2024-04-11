package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Rating;


public interface RatingRepository extends JpaRepository<Rating, Long>{
	Set<Rating> findByBook(Book book);
}
