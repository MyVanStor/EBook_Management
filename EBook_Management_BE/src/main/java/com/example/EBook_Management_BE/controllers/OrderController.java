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
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.order.IOrderService;
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
	private final IOrderService orderService;
	private final IBookService bookService;
	private final IUserService userService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws Exception {
		Order order = orderMapper.mapToOrderEntity(orderDTO);
		User user = userService.getUserById(orderDTO.getUserId());
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		for (Long bookId : orderDTO.getBookIds()) {
			Book book = bookService.getBookById(bookId);
			
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
		
		OrderResponse orderResponse = orderMapper.mapToOrderResponse(newOrder);

		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.ORDER_CREATE_SUCCESSFULLY))
				.data(orderResponse)
				.build());
	}

	@PutMapping("/{id}/{status}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateOrder(@PathVariable Long id, @PathVariable String status) throws Exception {
		Order order = orderService.getOrderById(id);
		order.setStatus(status);
		
	 	orderService.updateOrder(id, order);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.ORDER_UPDATE_SUCCESSFULLY))
				.data(order)
				.build());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseObject> getOrderById(@PathVariable Long id) throws Exception {
		Order order = orderService.getOrderById(id);
		
		OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.ORDER_GET_BY_ID_SUCCESSFULLY))
						.data(orderResponse)
						.build());
	}
}
