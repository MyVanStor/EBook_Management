package com.example.EBook_Management_BE.exceptions;

@SuppressWarnings("serial")
public class SelfFollowException extends Exception{
	public SelfFollowException (String message) {
		super(message);
	}
}
