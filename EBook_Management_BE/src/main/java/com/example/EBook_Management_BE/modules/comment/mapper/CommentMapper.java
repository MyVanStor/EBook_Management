package com.example.EBook_Management_BE.modules.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.common.entity.Comment;
import com.example.EBook_Management_BE.modules.comment.dto.CommentDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
	CommentMapper iNSTANCE = Mappers.getMapper(CommentMapper.class);

	Comment mapToCommentEntity(CommentDTO commentDTO);
}
