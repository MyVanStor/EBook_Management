package com.example.EBook_Management_BE.exceptions;

@SuppressWarnings("serial")
public class ExpiredTokenException extends Exception {
	public ExpiredTokenException(String message) {
		super(message);
	}
}
