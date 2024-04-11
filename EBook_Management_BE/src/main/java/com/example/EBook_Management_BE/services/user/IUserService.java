package com.example.EBook_Management_BE.services.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.EBook_Management_BE.dtos.UpdateUserDTO;
import com.example.EBook_Management_BE.dtos.UserDTO;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.InvalidPasswordException;

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
