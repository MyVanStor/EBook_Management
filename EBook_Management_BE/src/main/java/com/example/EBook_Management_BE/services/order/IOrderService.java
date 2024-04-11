package com.example.EBook_Management_BE.services.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.EBook_Management_BE.dtos.OrderDTO;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.responses.OrderResponse;

public interface IOrderService {
	Order createOrder(OrderDTO orderDTO);
	
	Order getOrderById(Long orderId);
	
	Order updateOrder(Long orderId, String status);
	
	void deleteOrder(long orderId);
	
	Page<OrderResponse> getAllOrderByUser(Long userId, PageRequest pageRequest);
}
