package com.example.EBook_Management_BE.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{

}
