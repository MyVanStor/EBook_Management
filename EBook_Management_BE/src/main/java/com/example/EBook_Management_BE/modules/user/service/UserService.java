package com.example.EBook_Management_BE.modules.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.components.LocalizationUtils;
import com.example.EBook_Management_BE.common.entity.Role;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.enums.RoleEnum;
import com.example.EBook_Management_BE.common.repository.RoleRepository;
import com.example.EBook_Management_BE.common.repository.UserRepository;
import com.example.EBook_Management_BE.common.utils.MessageKeys;
import com.example.EBook_Management_BE.modules.user.dto.UserDTO;
import com.example.EBook_Management_BE.modules.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;
	private final RoleRepository roleRepository;
	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public User createUser(UserDTO userDTO) throws Exception {
		String phoneNumber = userDTO.getPhoneNumber();

		if (userRepository.existsByPhoneNumber(phoneNumber)) {
			throw new DataIntegrityViolationException("Phone number is exits");
		}
		Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
				() -> new Exception(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));

		if (role.getName().toUpperCase().equals(RoleEnum.ADMIN)) {
			throw new Exception("Không được phép đăng ký tài khoản Admin");
		}

		User newUser = userMapper.mapToUserEntity(userDTO);
		newUser.setRole(role);

		return userRepository.save(newUser);
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException(String.format("User with id = %d not found", userId)));
	}

}
