package com.example.EBook_Management_BE.services.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.OrderDetailRepository;
import com.example.EBook_Management_BE.repositories.OrderRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	private final OrderRepository orderRepository;
	private final IOrderRedisService orderRedisService;
	private final OrderDetailRepository orderDetailRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Order createOrder(Order order) throws DataNotFoundException {
		Order newOrder = orderRepository.save(order);
		for (OrderDetail orderDetail : order.getOrderDetails()) {
			orderDetail.setOrder(newOrder);
			orderDetail = orderDetailRepository.save(orderDetail);

			newOrder.getOrderDetails().add(orderDetail);
		}

		return newOrder;
	}

	@Override
	public Order getOrderById(Long orderId) throws Exception {
		Order order = orderRedisService.getOrderById(orderId);
		if (order == null) {
			order = orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.ORDER_NOT_FOUND)));
			
			orderRedisService.saveOrderById(orderId, order);
		}
		return order;
	}

	@Override
	@Transactional
	public Order updateOrder(Long orderId, Order orderUpdate) throws Exception {
		return orderRepository.save(orderUpdate);
	}

}
