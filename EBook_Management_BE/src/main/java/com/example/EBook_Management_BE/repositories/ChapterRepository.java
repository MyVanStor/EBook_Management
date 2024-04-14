package com.example.EBook_Management_BE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
