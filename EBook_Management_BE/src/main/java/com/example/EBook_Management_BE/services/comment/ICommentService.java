package com.example.EBook_Management_BE.services.comment;

import com.example.EBook_Management_BE.entity.Comment;

public interface ICommentService {
	Comment getCommentById(Long commentId) throws Exception;
	
	Comment createComment(Comment comment);
	
	Comment updateComment(Long commentId, Comment comment) throws Exception;
	
	void deleteComment(Long commentId);
}	
