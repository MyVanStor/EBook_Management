package com.example.EBook_Management_BE.modules.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Comment;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.enums.CommentReplyType;
import com.example.EBook_Management_BE.common.repository.CommentRepository;
import com.example.EBook_Management_BE.modules.book.service.BookService;
import com.example.EBook_Management_BE.modules.comment.dto.CommentDTO;
import com.example.EBook_Management_BE.modules.comment.mapper.CommentMapper;
import com.example.EBook_Management_BE.modules.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
	private final CommentRepository commentRepository;
	private final UserService userService;
	private final BookService bookService;

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new RuntimeException(String.format("Comment with id = %d not found", commentId)));
	}

	@Override
	@Transactional
	public Comment createBookComment(CommentDTO commentDTO) {
		User user = userService.getUserById(commentDTO.getUserId());
		bookService.getBookById(commentDTO.getReplyId());

		Comment newComment = commentMapper.mapToCommentEntity(commentDTO);
		newComment.setReplyType(CommentReplyType.BOOK);
		newComment.setUser(user);

		return commentRepository.save(newComment);
	}

	@Override
	@Transactional
	public Comment createReplyComment(CommentDTO commentDTO) {
		User user = userService.getUserById(commentDTO.getUserId());
		getCommentById(commentDTO.getReplyId());

		Comment newComment = commentMapper.mapToCommentEntity(commentDTO);
		newComment.setReplyType(CommentReplyType.COMMENT);
		newComment.setUser(user);

		return commentRepository.save(newComment);
	}

	@Override
	@Transactional
	public Comment updateComment(Long commentId, CommentDTO commentDTO) {
		Comment existingComment = getCommentById(commentId);

		if (existingComment.getReplyId() == commentDTO.getReplyId()
				&& existingComment.getUser().getId() == commentDTO.getUserId()) {
			existingComment.setComment(commentDTO.getComment());
		}

		return commentRepository.save(existingComment);
	}

	@Override
	@Transactional
	public void deleteComment(Long commentId) {
		getCommentById(commentId);
		
		commentRepository.deleteById(commentId);
	}

}
