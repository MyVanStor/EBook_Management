package com.example.EBook_Management_BE.modules.comment.service;

import com.example.EBook_Management_BE.common.entity.Comment;
import com.example.EBook_Management_BE.modules.comment.dto.CommentDTO;

public interface ICommentService {
	Comment getCommentById(Long commentId);
	
	Comment createBookComment(CommentDTO commentDTO);
	
	Comment createReplyComment(CommentDTO commentDTO);
}	
