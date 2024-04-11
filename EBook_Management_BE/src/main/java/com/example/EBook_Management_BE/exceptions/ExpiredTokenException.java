package com.example.EBook_Management_BE.exceptions;

public class ExpiredTokenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpiredTokenException(String message) {
		super(message);
	}
}
