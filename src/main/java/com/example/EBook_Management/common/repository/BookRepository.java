package com.example.EBook_Management.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management.common.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
