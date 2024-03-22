package com.example.EBook_Management_BE.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.EBook_Management_BE.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o from Order o WHERE o.user.id = :userId")
	Page<Order> findAllByUser(Long userId, Pageable pageable);
}
