package com.example.EBook_Management_BE.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Token;
import com.example.EBook_Management_BE.common.entity.User;

public interface TokenRepository extends JpaRepository<Token, Long>{
	List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}
