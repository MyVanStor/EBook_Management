package com.example.EBook_Management_BE.services.order;

import com.example.EBook_Management_BE.entity.Order;

public interface IOrderService {
	Order createOrder(Order order) throws Exception;
	
	Order getOrderById(Long orderId) throws Exception;
	
	Order updateOrder(Long orderId, Order orderUpdate) throws Exception;
}
