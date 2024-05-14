package com.example.EBook_Management_BE.constants;

public class ValidPattern {
    public static final String FULLNAME_PATTERN = "^[\\p{L}\\s]+$";

    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-_=+{};:,<.>])(?=.{6,20}$).+$";
}
