package com.example.EBook_Management_BE.modules.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.common.exceptions.InvalidPasswordException;
import com.example.EBook_Management_BE.modules.user.dto.UpdateUserDTO;
import com.example.EBook_Management_BE.modules.user.dto.UserDTO;

public interface IUserService {
	User createUser(UserDTO userDTO) throws Exception;

	User getUserById(Long userId);

	User getUserDetailsFromToken(String token) throws Exception;

	User getUserDetailsFromRefreshToken(String refreshToken) throws Exception;

	String login(String phoneNumber, String password, Long roleId) throws Exception;

	User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

	Page<User> findAll(String keyword, Pageable pageable) throws Exception;

	void resetPassword(Long userId, String newPassword) throws InvalidPasswordException, DataNotFoundException;

	public void blockOrEnable(Long userId, short active) throws DataNotFoundException;
}
