package com.example.EBook_Management_BE.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.example.EBook_Management_BE.dtos.UpdateUserDTO;
import com.example.EBook_Management_BE.dtos.UserDTO;
import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.entity.Token;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.RoleEnum;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.ExpiredTokenException;
import com.example.EBook_Management_BE.exceptions.InvalidPasswordException;
import com.example.EBook_Management_BE.mappers.UserMapper;
import com.example.EBook_Management_BE.repositories.RoleRepository;
import com.example.EBook_Management_BE.repositories.TokenRepository;
import com.example.EBook_Management_BE.repositories.UserRepository;
import com.example.EBook_Management_BE.utils.MessageKeys;

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

	@Override
	@Transactional
	public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
		// Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the phone number is being changed and if it already exists for another user
        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
        if (!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
                userRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        // Update user information based on the DTO
        if (updatedUserDTO.getFullname() != null) {
            existingUser.setFullname(updatedUserDTO.getFullname());
        }
        if (newPhoneNumber != null) {
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if (updatedUserDTO.getLinkAvatar() != null) {
        	existingUser.setLinkAvatar(updatedUserDTO.getLinkAvatar());
        }
        if (updatedUserDTO.getGender() != existingUser.getGender()) {
        	existingUser.setLinkAvatar(updatedUserDTO.getLinkAvatar());
        }
        if (updatedUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
        }
        if (updatedUserDTO.getFacebookAccountId() > 0) {
            existingUser.setFacebookAccountId(updatedUserDTO.getFacebookAccountId());
        }
        if (updatedUserDTO.getGoogleAccountId() > 0) {
            existingUser.setGoogleAccountId(updatedUserDTO.getGoogleAccountId());
        }

        // Update the password if it is provided in the DTO
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = updatedUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        
        if (updatedUserDTO.getIsActive() != existingUser.getIsActive()) {
        	existingUser.setIsActive(updatedUserDTO.getIsActive());
        }

        return userRepository.save(existingUser);
	}

	@Override
	public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
		return userRepository.findAll(keyword, pageable);
	}

	@Override
	public void resetPassword(Long userId, String newPassword) throws InvalidPasswordException, DataNotFoundException {
		User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPassword(encodedPassword);
        userRepository.save(existingUser);
        //reset password => clear token
        List<Token> tokens = tokenRepository.findByUser(existingUser);
        for (Token token : tokens) {
            tokenRepository.delete(token);
        }
	}

	@Override
	public void blockOrEnable(Long userId, short active) throws DataNotFoundException {
		User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        existingUser.setIsActive(active);
        userRepository.save(existingUser);
		
	}

}
