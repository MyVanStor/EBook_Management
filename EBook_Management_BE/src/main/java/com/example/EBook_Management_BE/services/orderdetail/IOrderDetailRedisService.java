package com.example.EBook_Management_BE.services.orderdetail;

import com.example.EBook_Management_BE.entity.OrderDetail;

public interface IOrderDetailRedisService {
	void clearById(Long id);

	OrderDetail getOrderDetailById(Long orderDetailId) throws Exception;

	void saveOrderDetailById(Long orderDetailId, OrderDetail orderDetail) throws Exception;
}
