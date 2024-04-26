package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.ChapterDTO;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.responses.ChapterResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChapterMapper {
	ChapterMapper INSTANCE = Mappers.getMapper(ChapterMapper.class);
	
	Chapter mapToChapterEntity(ChapterDTO chapterDTO);
	
	Chapter mapToChapterEntity(ChapterResponse chapterResponse);
	
	ChapterResponse mapToChapterResponse(Chapter author);
}
