package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Rating;


public interface RatingRepository extends JpaRepository<Rating, Long>{
	Set<Rating> findByBook(Book book);
}
