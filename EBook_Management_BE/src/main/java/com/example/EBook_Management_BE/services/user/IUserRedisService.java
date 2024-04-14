package com.example.EBook_Management_BE.services.user;

import com.example.EBook_Management_BE.entity.User;

public interface IUserRedisService {
	void clearById(Long id);
	
	User getUserById(Long userId) throws Exception;
	
	void saveUserById(Long userId, User user) throws Exception;
	
	User getUserByTokenOrRefreshToken(String token) throws Exception;
	
	void saveUserByTokenOrRefreshToken(String token, User user) throws Exception;
	
	User getUserByPhoneNumber(String phoneNumber) throws Exception;
	
	void saveUserByPhoneNumber(String phoneNumber, User user) throws Exception;
}
