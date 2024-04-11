package com.example.EBook_Management_BE.services.orderdetail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.dtos.OrderDetailDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.repositories.OrderDetailRepository;
import com.example.EBook_Management_BE.services.book.BookService;
import com.example.EBook_Management_BE.services.order.OrderService;

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
