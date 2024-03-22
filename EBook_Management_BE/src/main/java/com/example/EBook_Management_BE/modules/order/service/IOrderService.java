package com.example.EBook_Management_BE.modules.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.EBook_Management_BE.common.entity.Order;
import com.example.EBook_Management_BE.modules.order.dto.OrderDTO;
import com.example.EBook_Management_BE.modules.order.response.OrderResponse;

public interface IOrderService {
	Order createOrder(OrderDTO orderDTO);
	
	Order getOrderById(Long orderId);
	
	Order updateOrder(Long orderId, String status);
	
	void deleteOrder(long orderId);
	
	Page<OrderResponse> getAllOrderByUser(Long userId, PageRequest pageRequest);
}
