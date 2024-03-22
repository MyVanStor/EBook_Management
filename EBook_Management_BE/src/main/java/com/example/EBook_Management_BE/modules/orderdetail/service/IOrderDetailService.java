package com.example.EBook_Management_BE.modules.orderdetail.service;

import com.example.EBook_Management_BE.common.entity.OrderDetail;
import com.example.EBook_Management_BE.modules.orderdetail.dto.OrderDetailDTO;

public interface IOrderDetailService {
	OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);
}
