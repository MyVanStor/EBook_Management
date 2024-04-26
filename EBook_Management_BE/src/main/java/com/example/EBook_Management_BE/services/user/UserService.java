package com.example.EBook_Management_BE.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.JwtTokenUtil;
import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Token;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.RoleEnum;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.exceptions.ExpiredTokenException;
import com.example.EBook_Management_BE.repositories.TokenRepository;
import com.example.EBook_Management_BE.repositories.UserRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	private final JwtTokenUtil jwtTokenUtil;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenRepository tokenRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public User createUser(User user) throws Exception {
		if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
			throw new DuplicateException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_DUPLICATE_USERNAME));
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRole().getName().toUpperCase().equals(RoleEnum.ADMIN)
				|| user.getRole().getName().toUpperCase().equals(RoleEnum.SYSADMIN)) {
			throw new Exception(localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_DOES_NOT_CREATE_ADMIN));
		}

		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long userId) throws DataNotFoundException {
		return userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException(
				localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_NOT_FOUND)));
	}

	@Override
	public String login(String phoneNumber, String password) throws Exception {
		Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

		if (optionalUser.isEmpty()) {
			throw new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_INVALID_USERNAME_OR_PASSWORD));
		}

		User existingUser = optionalUser.get();
		if (existingUser.getFacebookAccountId() == 0 || existingUser.getGoogleAccountId() == 0) {
			if (!passwordEncoder.matches(password, existingUser.getPassword())) {
				throw new BadCredentialsException("Wrong phone number or password");
			}
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber,
				password);
		authenticationManager.authenticate(authenticationToken);

		return jwtTokenUtil.generateToken(existingUser);
	}

	@Override
	public User getUserDetailsFromToken(String token) throws Exception {
		if (jwtTokenUtil.isTokenExpired(token)) {
			throw new ExpiredTokenException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.TOKEN_IS_EXPRIED));
		}

		String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
		Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

		if (user.isPresent()) {
			return user.get();
		} else {
			throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_NOT_FOUND));
		}
	}

	@Override
	public User getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
		Token existingToken = tokenRepository.findByRefreshToken(refreshToken);

		return getUserDetailsFromToken(existingToken.getToken());
	}

	@Override
	@Transactional
	public User updateUser(Long userId, User user) throws Exception {
		// Find the existing user by userId
		User existingUser = getUserById(userId);

		// Update the password if it is provided in the DTO
		if (user.getPassword() != null) {
			String newPassword = user.getPassword();
			String encodedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encodedPassword);
		}
		if (user.getFullname() != existingUser.getFullname()) {
			existingUser.setFullname(user.getFullname());
		}
		if (user.getLinkAvatar() != existingUser.getLinkAvatar()) {
			existingUser.setLinkAvatar(user.getLinkAvatar());
		}
		if (user.getGender() != existingUser.getGender()) {
			existingUser.setGender(user.getGender());
		}
		if (user.getDateOfBirth() != existingUser.getDateOfBirth()) {
			existingUser.setDateOfBirth(user.getDateOfBirth());
		}
		if (user.getIsActive() != existingUser.getIsActive()) {
			existingUser.setIsActive(user.getIsActive());
		}

		return userRepository.save(existingUser);
	}

	@Override
	public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
		return userRepository.findAll(keyword, pageable);
	}

	@Override
	@Transactional
	public void resetPassword(String phoneNumber, String newPassword) throws DataNotFoundException {
		User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new DataNotFoundException(
				localizationUtils.getLocalizedMessage(MessageExceptionKeys.USER_NOT_FOUND)));

		// reset password => clear token
		List<Token> tokens = tokenRepository.findByUser(existingUser);
		for (Token token : tokens) {
			tokenRepository.delete(token);
		}

		String encodedPassword = passwordEncoder.encode(newPassword);
		existingUser.setPassword(encodedPassword);
		userRepository.save(existingUser);
	}

	@Override
	public void blockOrEnable(Long userId, short active) throws DataNotFoundException {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new DataNotFoundException("User not found"));
		existingUser.setIsActive(active);

		userRepository.save(existingUser);
	}

}
