package com.example.EBook_Management_BE.services.comment;

import com.example.EBook_Management_BE.entity.Comment;

public interface ICommentRedisService {
	void clearById(Long id);

	Comment getCommentById(Long commentId) throws Exception;

	void saveCommentById(Long commentId, Comment comment) throws Exception;
}
