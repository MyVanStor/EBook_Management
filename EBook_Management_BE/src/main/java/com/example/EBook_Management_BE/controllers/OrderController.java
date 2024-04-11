package com.example.EBook_Management_BE.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.OrderDTO;
import com.example.EBook_Management_BE.dtos.OrderDetailDTO;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.responses.OrderResponse;
import com.example.EBook_Management_BE.services.book.BookService;
import com.example.EBook_Management_BE.services.order.OrderService;
import com.example.EBook_Management_BE.services.orderdetail.OrderDetailService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.ORDER)
@Validated
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final LocalizationUtils localizationUtils;
	private final OrderDetailService orderDetailService;
	private final BookService bookService;

	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
		OrderResponse orderResponse = new OrderResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			orderResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
			orderResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(orderResponse);
		}

		Order newOrder = orderService.createOrder(orderDTO);

		Set<OrderDetail> orderDetails = new HashSet<>();
		for (Long bookId : orderDTO.getBookIds()) {
			OrderDetailDTO orderDetailDTO = OrderDetailDTO.builder().price(bookService.getBookById(bookId).getPrice())
					.orderId(newOrder.getId()).bookId(bookId).build();
			orderDetails.add(orderDetailService.createOrderDetail(orderDetailDTO));
		}

		newOrder.setOrderDetails(orderDetails);
		orderResponse.setOrder(newOrder);

		return ResponseEntity.created(null).body(orderResponse);
	}

	@PutMapping("/{id}/{status}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateOrder(@PathVariable Long id, @PathVariable String status) {
		Order order = orderService.updateOrder(id, status);
		return ResponseEntity.ok(ResponseObject.builder().data(order)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFULLY)).build());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> getOrderById(@PathVariable Long id) {
		Order order = orderService.getOrderById(id);
		return ResponseEntity
				.ok(ResponseObject.builder().message("Get order success").data(order).status(HttpStatus.OK).build());
	}

	@GetMapping("/all/{userId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> getOrderByUser(@PathVariable Long userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int limit) {
		// Tạo Pageable từ thông tin trang và giới hạn
		PageRequest pageRequest = PageRequest.of(page, limit,
				// Sort.by("createdAt").descending()
				Sort.by("id").ascending());

		Page<OrderResponse> orderPage = orderService.getAllOrderByUser(userId, pageRequest);
		
		if (orderPage.getTotalPages() <= page) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder()
					.status(HttpStatus.NOT_FOUND)
					.message("Limit > totalPage")
					.build());
		}
		
		if (orderPage.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseObject.builder()
					.status(HttpStatus.NOT_FOUND)
					.message(String.format("Order with user_id = %d not found", userId))
					.build());
		} 
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message("Success")
				.data(orderPage)
				.build());
	}
}
