package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.CommentDTO;
import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.entity.Comment;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.CommentReplyType;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.CommentMapper;
import com.example.EBook_Management_BE.responses.CommentResponse;
import com.example.EBook_Management_BE.services.book.IBookRedisService;
import com.example.EBook_Management_BE.services.book.IBookService;
import com.example.EBook_Management_BE.services.chapter.IChapterRedisService;
import com.example.EBook_Management_BE.services.chapter.IChapterService;
import com.example.EBook_Management_BE.services.comment.ICommentRedisService;
import com.example.EBook_Management_BE.services.comment.ICommentService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.COMMENT)
@Validated
@RequiredArgsConstructor
public class CommentController {
	private final ICommentService commentService;
	private final ICommentRedisService commentRedisService;
	private final IUserService userService;
	private final IUserRedisService userRedisService;
	private final IBookService bookService;
	private final IBookRedisService bookRedisService;
	private final IChapterService chapterService;
	private final IChapterRedisService chapterRedisService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> getCommmentById(@PathVariable Long id) throws Exception {
		Comment existingComment = commentRedisService.getCommentById(id);
		if (existingComment == null) {
			existingComment = commentService.getCommentById(id);
			
			commentRedisService.saveCommentById(id, existingComment);
		}
		
		CommentResponse commentResponse = commentMapper.mapToCommentResponse(existingComment);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_GET_BY_ID_SUCCESSFULLY))
				.data(commentResponse)
				.build());
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createComment(@Valid @RequestBody CommentDTO commentDTO) throws Exception {
		User user = userRedisService.getUserById(commentDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(commentDTO.getUserId());
			
			userRedisService.saveUserById(commentDTO.getUserId(), user);
		}
		
		if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.BOOK)) {
			Book book = bookRedisService.getBookById(commentDTO.getReplyId());
			if (book == null) {
				book = bookService.getBookById(commentDTO.getReplyId());
			
				bookRedisService.saveBookById(commentDTO.getReplyId(), book); 
			}
		} else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.COMMENT)) {
			Comment comment = commentRedisService.getCommentById(commentDTO.getReplyId());
			if (comment == null) {
				comment = commentService.getCommentById(commentDTO.getReplyId());
				
				commentRedisService.saveCommentById(commentDTO.getReplyId(), comment);
			}
		} else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.CHAPTER)) {
			Chapter chapter = chapterRedisService.getChapterById(commentDTO.getReplyId());
			if (chapter == null) {
				chapter = chapterService.getChapterById(commentDTO.getReplyId());
				
				chapterRedisService.saveChapterById(commentDTO.getReplyId(), chapter);
			}
		}
		
		Comment comment = commentMapper.mapToCommentEntity(commentDTO);
		comment.setUser(user);
		
		Comment newComment = commentService.createComment(comment);
		commentRedisService.saveCommentById(newComment.getId(), newComment);

		CommentResponse commentResponse = commentMapper.mapToCommentResponse(newComment);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_CREATE_SUCCESSFULLY))
				.data(commentResponse)
				.build());
	}
	
	@PutMapping("/{commentId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentDTO commentDTO) throws Exception {
		User user = userRedisService.getUserById(commentDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(commentDTO.getUserId());
			
			userRedisService.saveUserById(commentDTO.getUserId(), user);
		}
		
		if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.BOOK)) {
			Book book = bookRedisService.getBookById(commentDTO.getReplyId());
			if (book == null) {
				book = bookService.getBookById(commentDTO.getReplyId());
			
				bookRedisService.saveBookById(commentDTO.getReplyId(), book); 
			}
		} else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.COMMENT)) {
			Comment comment = commentRedisService.getCommentById(commentDTO.getReplyId());
			if (comment == null) {
				comment = commentService.getCommentById(commentDTO.getReplyId());
				
				commentRedisService.saveCommentById(commentDTO.getReplyId(), comment);
			}
		} else if (commentDTO.getReplyType().toUpperCase().equals(CommentReplyType.CHAPTER)) {
			Chapter chapter = chapterRedisService.getChapterById(commentDTO.getReplyId());
			if (chapter == null) {
				chapter = chapterService.getChapterById(commentDTO.getReplyId());
				
				chapterRedisService.saveChapterById(commentDTO.getReplyId(), chapter);
			}
		}
		
		Comment comment = commentMapper.mapToCommentEntity(commentDTO);
		comment.setUser(user);
		
		comment = commentService.updateComment(commentId, comment);
		commentRedisService.saveCommentById(commentId, comment);
		
		CommentResponse commentResponse = commentMapper.mapToCommentResponse(comment);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_UPDATE_SUCCESSFULLY))
				.data(commentResponse)
				.build());
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> deleteComment(@PathVariable Long id) throws Exception {
		commentService.deleteComment(id);
		
		return ResponseEntity
				.ok(ResponseObject.builder()
						.status(HttpStatus.OK)
						.message(localizationUtils.getLocalizedMessage(MessageKeys.COMMENT_DELETE_SUCCESSFULLY))
						.build());
	}
}
