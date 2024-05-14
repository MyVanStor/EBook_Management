package com.example.EBook_Management_BE.services.comment;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Comment;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

import java.util.List;

public interface ICommentService {
	Comment getCommentById(Long commentId) throws Exception;
	
	Comment createComment(Comment comment);
	
	Comment updateComment(Long commentId, Comment comment) throws Exception;
	
	void deleteComment(Long commentId);

	List<Comment> getAllComment(Long replyId, String replyType) throws DataNotFoundException;
}	
