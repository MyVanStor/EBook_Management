package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.PainterDTO;
import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.responses.PainterResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PainterMapper {
	PainterMapper iNSTANCE = Mappers.getMapper(PainterMapper.class);
	
	Painter mapToPainterEntity(PainterDTO painterDTO);
	
	Painter mapToPainterEntity(PainterResponse painterResponse);
	
	PainterResponse mapToPainterResponse(Painter painter);
}
