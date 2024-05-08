package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.PainterDTO;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.responses.PainterResponse;

@Mapper(componentModel = "spring")
public interface PainterMapper {
	PainterMapper INSTANCE = Mappers.getMapper(PainterMapper.class);
	
	Painter mapToPainterEntity(PainterDTO painterDTO);
	
	Painter mapToPainterEntity(PainterResponse painterResponse);
	
	PainterResponse mapToPainterResponse(Painter painter);
}
