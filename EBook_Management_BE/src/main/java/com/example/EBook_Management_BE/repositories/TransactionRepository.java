package com.example.EBook_Management_BE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
