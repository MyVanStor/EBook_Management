package com.example.EBook_Management_BE.modules.token.service;

import com.example.EBook_Management_BE.common.entity.Token;
import com.example.EBook_Management_BE.common.entity.User;

public interface ITokenService {
	Token addToken(User user, String token, boolean isMobileDevice);
	
    Token refreshToken(String refreshToken, User user) throws Exception;
}
