package com.example.EBook_Management_BE.modules.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.components.JwtTokenUtil;
import com.example.EBook_Management_BE.common.components.LocalizationUtils;
import com.example.EBook_Management_BE.common.entity.Role;
import com.example.EBook_Management_BE.common.entity.Token;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.enums.RoleEnum;
import com.example.EBook_Management_BE.common.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.common.exceptions.ExpiredTokenException;
import com.example.EBook_Management_BE.common.repository.RoleRepository;
import com.example.EBook_Management_BE.common.repository.TokenRepository;
import com.example.EBook_Management_BE.common.repository.UserRepository;
import com.example.EBook_Management_BE.common.utils.MessageKeys;
import com.example.EBook_Management_BE.modules.user.dto.UserDTO;
import com.example.EBook_Management_BE.modules.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenRepository tokenRepository;

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
			throw new Exception("Cannot register account admin");
		}

		User newUser = userMapper.mapToUserEntity(userDTO);
		newUser.setRole(role);

		// Kiểm tra nếu có accountId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        
		return userRepository.save(newUser);
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException(String.format("User with id = %d not found", userId)));
	}

	@Override
	public String login(String phoneNumber, String password, Long roleId) throws Exception {
		Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
		
		if (optionalUser.isEmpty()) {
			throw new DataNotFoundException("Invalid phone number or password");
		}
		
		User existingUser = optionalUser.get();
		if (existingUser.getFacebookAccountId() == 0 || existingUser.getGoogleAccountId() == 0) {
			if (!passwordEncoder.matches(password, existingUser.getPassword())) {
				throw new BadCredentialsException("Wrong phone number or password");
			}
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password);
		authenticationManager.authenticate(authenticationToken);
		
		return jwtTokenUtil.generateToken(existingUser);
	}

	@Override
	public User getUserDetailsFromToken(String token) throws Exception {
		if(jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
	}

	@Override
	public User getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
		Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        return getUserDetailsFromToken(existingToken.getToken());
	}

}
