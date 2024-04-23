package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.ReadingHistoryDTO;
import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.example.EBook_Management_BE.responses.ReadingHistoryResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReadingHistoryMapper {
	ReadingHistoryMapper iNSTANCE = Mappers.getMapper(ReadingHistoryMapper.class);

	ReadingHistory mapToReadingHistoryEntity(ReadingHistoryDTO readingHistoryDTO);

	ReadingHistory mapToReadingHistoryEntity(ReadingHistoryResponse readingHistoryResponse);

	ReadingHistoryResponse mapToReadingHistoryResponse(ReadingHistory readingHistory);
}
