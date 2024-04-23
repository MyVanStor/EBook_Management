package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.entity.User;

public interface ReadingHistoryRepository extends JpaRepository<ReadingHistory, Long>{
	Set<ReadingHistory> findByUser(User user);
}
