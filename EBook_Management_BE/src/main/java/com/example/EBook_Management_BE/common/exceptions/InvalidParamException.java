package com.example.EBook_Management_BE.common.exceptions;

@SuppressWarnings("serial")
public class InvalidParamException extends Exception{
    public InvalidParamException(String message) {
        super(message);
    }
}
