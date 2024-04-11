package com.example.EBook_Management_BE.services.orderdetail;

import com.example.EBook_Management_BE.dtos.OrderDetailDTO;
import com.example.EBook_Management_BE.entity.OrderDetail;

public interface IOrderDetailService {
	OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);
}
