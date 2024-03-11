package com.example.EBook_Management_BE.modules.comment.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.common.components.LocalizationUtils;
import com.example.EBook_Management_BE.common.entity.Comment;
import com.example.EBook_Management_BE.common.enums.Uri;
import com.example.EBook_Management_BE.common.utils.MessageKeys;
import com.example.EBook_Management_BE.common.utils.ResponseObject;
import com.example.EBook_Management_BE.modules.comment.dto.CommentDTO;
import com.example.EBook_Management_BE.modules.comment.response.CommentResponse;
import com.example.EBook_Management_BE.modules.comment.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.COMMENT)
@Validated
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	private final LocalizationUtils localizationUtils;
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getCommmentById(@PathVariable Long id) {
		Comment existingComment = commentService.getCommentById(id);
		return ResponseEntity.ok(ResponseObject.builder().data(existingComment)
				.message("Get comment information successfully").status(HttpStatus.OK).build());
	}

	@PostMapping("/book")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CommentResponse> createBookComment(@Valid @RequestBody CommentDTO commentDTO,
			BindingResult result) {
		CommentResponse commentResponse = new CommentResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			commentResponse
					.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
			commentResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(commentResponse);
		}

		Comment comment = commentService.createBookComment(commentDTO);
		commentResponse.setComment(comment);
		return ResponseEntity.created(null).body(commentResponse);
	}
	
	@PostMapping("/reply")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<CommentResponse> createReplyComment(@Valid @RequestBody CommentDTO commentDTO,
			BindingResult result) {
		CommentResponse commentResponse = new CommentResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			commentResponse
					.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY));
			commentResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(commentResponse);
		}

		Comment comment = commentService.createReplyComment(commentDTO);
		commentResponse.setComment(comment);
		return ResponseEntity.created(null).body(commentResponse);
	}
}
