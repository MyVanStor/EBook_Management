package com.example.EBook_Management_BE.services.order;

import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface IOrderService {
	Order createOrder(Order order) throws DataNotFoundException;
	
	Order getOrderById(Long orderId) throws DataNotFoundException;
	
	Order updateOrder(Long orderId, Order orderUpdate) throws Exception;
}
