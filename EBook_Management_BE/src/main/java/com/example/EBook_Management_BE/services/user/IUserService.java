package com.example.EBook_Management_BE.services.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface IUserService {
	User createUser(User user) throws Exception;

	User getUserById(Long userId) throws DataNotFoundException;

	User getUserDetailsFromToken(String token) throws Exception;

	User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

	String login(String phoneNumber, String password) throws Exception;

	User updateUser(Long userId, User user) throws Exception;

	Page<User> findAll(String keyword, Pageable pageable) throws Exception;

	void resetPassword(String phoneNumber, String newPassword) throws DataNotFoundException;

	public void blockOrEnable(Long userId, short active) throws DataNotFoundException;
}
