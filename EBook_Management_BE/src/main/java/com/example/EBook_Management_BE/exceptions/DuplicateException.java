package com.example.EBook_Management_BE.exceptions;

@SuppressWarnings("serial")
public class DuplicateException extends Exception{
	public DuplicateException (String message) {
		super(message);
	}
}
