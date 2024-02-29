package com.example.EBook_Management_BE.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

}
