package com.example.EBook_Management_BE.services.token;

import com.example.EBook_Management_BE.entity.Token;

public interface ITokenRedisService {
	void clear(String tokenString);

	Token getToken(String tokenString) throws Exception;

	void saveToken(String tokenString, Token token) throws Exception;
}
