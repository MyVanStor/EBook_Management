package com.example.EBook_Management.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EBook_Management.common.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

}
