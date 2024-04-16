package com.example.EBook_Management_BE.services.order;

import com.example.EBook_Management_BE.entity.Order;

public interface IOrderRedisService {
	void clearById(Long id);

	Order getOrderById(Long orderId) throws Exception;

	void saveOrderById(Long orderId, Order order) throws Exception;
}
