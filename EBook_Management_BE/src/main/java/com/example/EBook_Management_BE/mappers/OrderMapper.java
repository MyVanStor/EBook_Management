package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.OrderDTO;
import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.responses.OrderResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	Order mapToOrderEntity(OrderDTO orderDTO);
	
	OrderResponse mapToOrderResponse(Order order);
}
