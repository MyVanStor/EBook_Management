package com.example.EBook_Management_BE.modules.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Order;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.repository.OrderRepository;
import com.example.EBook_Management_BE.modules.book.service.BookService;
import com.example.EBook_Management_BE.modules.order.dto.OrderDTO;
import com.example.EBook_Management_BE.modules.order.mapper.OrderMapper;
import com.example.EBook_Management_BE.modules.order.response.OrderResponse;
import com.example.EBook_Management_BE.modules.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	private final OrderRepository orderRepository;
	private final UserService userService;
	private final BookService bookService;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	@Transactional
	public Order createOrder(OrderDTO orderDTO) {
		double totalMoney = 0;
		User user = userService.getUserById(orderDTO.getUserId());

		Order newOrder = orderMapper.mapToOrderEntity(orderDTO);
		newOrder.setUser(user);
		
		for (Long bookId : orderDTO.getBookIds()) {
			totalMoney += bookService.getBookById(bookId).getPrice();
		}
		newOrder.setTotalMoney(totalMoney);

		return orderRepository.save(newOrder);
	}

	@Override
	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException(String.format("Order with id = %d not found", orderId)));
	}

	@Override
	@Transactional
	public Order updateOrder(Long orderId, String status) {
		Order existingOrder = getOrderById(orderId);
		
		existingOrder.setStatus(status);
		orderRepository.save(existingOrder);
		
		return existingOrder;
	}

	@Override
	public void deleteOrder(long orderId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<OrderResponse> getAllOrderByUser(Long userId, PageRequest pageRequest) {
		userService.getUserById(userId);
		
		Page<Order> orderPage;
		orderPage = orderRepository.findAllByUser(userId, pageRequest);
		
		return orderPage.map(order -> orderMapper.mapToOrderResponse(order));
	}

}
