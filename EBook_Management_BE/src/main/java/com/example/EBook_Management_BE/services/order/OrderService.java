package com.example.EBook_Management_BE.services.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.entity.OrderDetail;
import com.example.EBook_Management_BE.entity.UserBook;
import com.example.EBook_Management_BE.constants.StatusUserBook;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.repositories.OrderDetailRepository;
import com.example.EBook_Management_BE.repositories.OrderRepository;
import com.example.EBook_Management_BE.repositories.UserBookRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	private final OrderRepository orderRepository;
	private final IOrderRedisService orderRedisService;
	private final OrderDetailRepository orderDetailRepository;
	private final UserBookRepository userBookRepository;
	

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public Order createOrder(Order order) throws Exception {
		// Tạo một danh sách để lưu trữ các orderDetail đã lưu thành công
	    List<OrderDetail> savedOrderDetails = new ArrayList<>();

	    // Lưu thông tin UserBook trước
	    for (OrderDetail orderDetail : order.getOrderDetails()) {
	        UserBook userBook = UserBook.builder()
	                .status(StatusUserBook.BUYER)
	                .book(orderDetail.getBook())
	                .user(order.getUser())
	                .build();

	        if (userBookRepository.existsByUserAndBook(userBook.getUser(), userBook.getBook())) {
	            throw new DuplicateException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_BOOK_DUPLICATE_USER_AND_BOOK));
	        }

	        userBookRepository.save(userBook);

	        // Sau khi lưu thành công, thêm orderDetail vào danh sách savedOrderDetails
	        savedOrderDetails.add(orderDetail);
	    }

	    // Sau khi lưu thông tin UserBook, lưu orderDetail và order
	    Order newOrder = orderRepository.save(order);
	    for (OrderDetail savedOrderDetail : savedOrderDetails) {
	        savedOrderDetail.setOrder(newOrder);
	        savedOrderDetail = orderDetailRepository.save(savedOrderDetail);

	        newOrder.getOrderDetails().add(savedOrderDetail);
	    }
	    orderRedisService.saveOrderById(newOrder.getId(), newOrder);

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
		orderRepository.save(orderUpdate);
		orderRedisService.saveOrderById(orderId, orderUpdate);
		
		return orderUpdate;
	}

}
