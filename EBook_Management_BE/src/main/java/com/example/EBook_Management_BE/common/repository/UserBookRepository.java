package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.entity.UserBook;



public interface UserBookRepository extends JpaRepository<UserBook, Long>{
	Set<UserBook> findByBookAndUser(Book book, User user);
}
