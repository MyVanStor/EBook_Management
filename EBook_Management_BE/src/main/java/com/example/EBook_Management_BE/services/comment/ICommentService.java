package com.example.EBook_Management_BE.services.comment;

import com.example.EBook_Management_BE.entity.Comment;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface ICommentService {
	Comment getCommentById(Long commentId) throws DataNotFoundException;
	
	Comment createComment(Comment comment);
	
	Comment updateComment(Long commentId, Comment comment) throws Exception;
	
	void deleteComment(Long commentId);
}	
