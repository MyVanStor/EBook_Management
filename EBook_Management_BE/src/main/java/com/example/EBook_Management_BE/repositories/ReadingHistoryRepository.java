package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import com.example.EBook_Management_BE.entity.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.entity.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReadingHistoryRepository extends JpaRepository<ReadingHistory, Long>{
	@Query("SELECT rh FROM ReadingHistory rh WHERE rh.user = :user ORDER BY rh.updatedAt DESC")
	Set<ReadingHistory> getAllByUser(@Param("user") User user);

	boolean existsByBookAndUser(Book book, User user);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT rh FROM ReadingHistory rh WHERE rh.book = :book AND rh.user = :user")
	ReadingHistory findByBookAndUserForUpdate(@Param("book") Book book, @Param("user") User user);
}
