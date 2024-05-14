package com.example.EBook_Management_BE.services.comment;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Comment;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.CommentRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

import java.rmi.NotBoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
	private final CommentRepository commentRepository;
	private final ICommentRedisService commentRedisService;

	private final LocalizationUtils localizationUtils;

	@Override
	public Comment getCommentById(Long commentId) throws Exception {
		Comment comment = commentRedisService.getCommentById(commentId);
		if (comment == null) {
			comment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException(
				localizationUtils.getLocalizedMessage(MessageExceptionKeys.COMMENT_NOT_FOUND)));
			
			commentRedisService.saveCommentById(commentId, comment);
		}
		
		return comment; 
	}

	@Override
	@Transactional
	public Comment createComment(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	@Transactional
	public Comment updateComment(Long commentId, Comment comment) throws Exception {
		Comment existingComment = getCommentById(commentId);

		comment.setId(existingComment.getId());
		commentRepository.save(comment);

		return comment;
	}

	@Override
	@Transactional
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Override
	public List<Comment> getAllComment(Long replyId, String replyType) throws DataNotFoundException {
		List<Comment> comments = commentRepository.findByReplyIdAndReplyType(replyId, replyType);
		if (comments.isEmpty()) {
			throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.COMMENT_NOT_FOUND));
		}

		return comments;
	}
}
