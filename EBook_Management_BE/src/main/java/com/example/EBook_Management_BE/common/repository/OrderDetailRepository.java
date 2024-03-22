package com.example.EBook_Management_BE.common.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Order;
import com.example.EBook_Management_BE.common.entity.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
	Set<OrderDetail> findByOrderAndBook(Order order, Book book);
}
