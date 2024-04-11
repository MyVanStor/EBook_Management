package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.CommentDTO;
import com.example.EBook_Management_BE.entity.Comment;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
	CommentMapper iNSTANCE = Mappers.getMapper(CommentMapper.class);

	Comment mapToCommentEntity(CommentDTO commentDTO);
}
