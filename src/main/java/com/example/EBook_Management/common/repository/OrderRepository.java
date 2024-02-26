package com.example.EBook_Management.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
