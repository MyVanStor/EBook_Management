package com.example.EBook_Management_BE.common.exceptions;

@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message) {
        super(message);
    }
}