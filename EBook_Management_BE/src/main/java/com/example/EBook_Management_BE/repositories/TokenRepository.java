package com.example.EBook_Management_BE.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Token;
import com.example.EBook_Management_BE.entity.User;


public interface TokenRepository extends JpaRepository<Token, Long>{
	List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}
