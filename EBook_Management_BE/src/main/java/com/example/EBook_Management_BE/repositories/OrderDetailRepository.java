package com.example.EBook_Management_BE.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.entity.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
	Set<OrderDetail> findByOrderAndBook(Order order, Book book);
}
