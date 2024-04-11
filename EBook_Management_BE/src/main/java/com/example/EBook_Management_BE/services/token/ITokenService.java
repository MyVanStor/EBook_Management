package com.example.EBook_Management_BE.services.token;

import com.example.EBook_Management_BE.entity.Token;
import com.example.EBook_Management_BE.entity.User;


public interface ITokenService {
	Token addToken(User user, String token, boolean isMobileDevice);
	
    Token refreshToken(String refreshToken, User user) throws Exception;
}
