package com.example.EBook_Management_BE.exceptions;

@SuppressWarnings("serial")
public class InvalidParamException extends Exception{
    public InvalidParamException(String message) {
        super(message);
    }
}
