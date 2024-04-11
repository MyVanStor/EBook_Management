package com.example.EBook_Management_BE.services.comment;

import com.example.EBook_Management_BE.dtos.CommentDTO;
import com.example.EBook_Management_BE.entity.Comment;

public interface ICommentService {
	Comment getCommentById(Long commentId);
	
	Comment createBookComment(CommentDTO commentDTO);
	
	Comment createReplyComment(CommentDTO commentDTO);
	
	Comment updateComment(Long commentId, CommentDTO commentDTO);
	
	void deleteComment(Long commentId);
}	
