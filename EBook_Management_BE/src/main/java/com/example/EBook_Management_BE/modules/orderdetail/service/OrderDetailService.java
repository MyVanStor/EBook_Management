package com.example.EBook_Management_BE.modules.orderdetail.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Book;
import com.example.EBook_Management_BE.common.entity.Order;
import com.example.EBook_Management_BE.common.entity.OrderDetail;
import com.example.EBook_Management_BE.common.repository.OrderDetailRepository;
import com.example.EBook_Management_BE.modules.book.service.BookService;
import com.example.EBook_Management_BE.modules.order.service.OrderService;
import com.example.EBook_Management_BE.modules.orderdetail.dto.OrderDetailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
	private final OrderDetailRepository orderDetailRepository;
	private final BookService bookService;
	private final OrderService orderService;

	@Override
	@Transactional
	public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
		Order order = orderService.getOrderById(orderDetailDTO.getOrderId());

		Book book = bookService.getBookById(orderDetailDTO.getBookId());

		OrderDetail orderDetail = OrderDetail.builder()
				.price(book.getPrice())
				.book(book)
				.order(order)
				.build();
		
		return orderDetailRepository.save(orderDetail);
	}

}
