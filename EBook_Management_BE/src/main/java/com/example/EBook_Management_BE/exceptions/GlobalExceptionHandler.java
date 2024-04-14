package com.example.EBook_Management_BE.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final LocalizationUtils localizationUtils;
	
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleDataNotFoundException(DataNotFoundException exception) {
	    return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(exception.getMessage())
	    		.build());
	}
	
	@ExceptionHandler(DuplicateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleDuplicatePainterException(DuplicateException exception) {
		return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(exception.getMessage())	
	    		.build());
	}
	
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleBindException(BindException e) {
	    return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(localizationUtils.getLocalizedMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()))	
	    		.build());
	}
	
	@ExceptionHandler(DeleteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleDeleteException(DeleteException exception) {
	    return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(localizationUtils.getLocalizedMessage(exception.getMessage()))	
	    		.build());
	}
	
	@ExceptionHandler(ExpiredTokenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleExpiredTokenException(ExpiredTokenException exception) {
	    return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(localizationUtils.getLocalizedMessage(exception.getMessage()))	
	    		.build());
	}
	
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleIllegalStateException(IllegalStateException exception) {
	    return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(exception.getMessage())	
	    		.build());
	}
	
	@ExceptionHandler(SelfFollowException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseObject> handleSelfFollowException(SelfFollowException exception) {
	    return ResponseEntity.badRequest().body(ResponseObject.builder()
	    		.status(HttpStatus.BAD_REQUEST)
	    		.message(exception.getMessage())	
	    		.build());
	}
}
