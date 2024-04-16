package com.example.EBook_Management_BE.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.OrderDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.OrderMapper;
import com.example.EBook_Management_BE.responses.OrderResponse;
import com.example.EBook_Management_BE.services.book.IBookRedisService;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.order.IOrderRedisService;
import com.example.EBook_Management_BE.services.order.IOrderService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.ORDER)
@Validated
@RequiredArgsConstructor
public class OrderController {
	private final IOrderRedisService orderRedisService;
	private final IOrderService orderService;
	private final IBookRedisService bookRedisService;
	private final IBookService bookService;
	private final IUserRedisService userRedisService;
	private final IUserService userService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws Exception {
		Order order = orderMapper.mapToOrderEntity(orderDTO);
		User user = userRedisService.getUserById(orderDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(orderDTO.getUserId());
			
			userRedisService.saveUserById(user.getId(), user);
		}
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		for (Long bookId : orderDTO.getBookIds()) {
			Book book = bookRedisService.getBookById(bookId);
			if (book == null) {
				book = bookService.getBookById(bookId);
				
				bookRedisService.saveBookById(bookId, book);
			}
			OrderDetail orderDetail = OrderDetail.builder()
					.price(book.getPrice())
					.book(book)
					.build();
			order.setTotalMoney(orderDetail.getPrice() + order.getTotalMoney());
			orderDetails.add(orderDetail);
		}
		
		order.setUser(user);
		order.setOrderDetails(orderDetails);
		
		Order newOrder = orderService.createOrder(order);
		orderRedisService.saveOrderById(newOrder.getId(), newOrder);
		
		OrderResponse orderResponse = orderMapper.mapToOrderResponse(newOrder);

		newOrder.setOrderDetails(orderDetails);

		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.ORDER_CREATE_SUCCESSFULLY))
				.data(orderResponse)
				.build());
	}

	@PutMapping("/{id}/{status}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateOrder(@PathVariable Long id, @PathVariable String status) throws Exception {
		Order order = orderRedisService.getOrderById(id);
		if (order == null) {
			order = orderService.getOrderById(id);
		}
		order.setStatus(status);
		
	 	orderService.updateOrder(id, order);
	 	orderRedisService.saveOrderById(id, order);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.ORDER_UPDATE_SUCCESSFULLY))
				.data(order)
				.build());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> getOrderById(@PathVariable Long id) throws Exception {
		Order order = orderRedisService.getOrderById(id);
		if (order == null) {
			order = orderService.getOrderById(id);
			
			orderRedisService.saveOrderById(id, order);
		}
		OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.ORDER_GET_BY_ID_SUCCESSFULLY))
						.data(orderResponse)
						.build());
	}
}
