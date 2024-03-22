package com.example.EBook_Management_BE.modules.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.common.entity.Order;
import com.example.EBook_Management_BE.modules.order.dto.OrderDTO;
import com.example.EBook_Management_BE.modules.order.response.OrderResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
	OrderMapper iNSTANCE = Mappers.getMapper(OrderMapper.class);
	
	Order mapToOrderEntity(OrderDTO orderDTO);
	
	OrderResponse mapToOrderResponse(Order order);
}
