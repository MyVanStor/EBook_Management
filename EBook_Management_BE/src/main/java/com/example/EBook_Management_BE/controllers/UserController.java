package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.RefreshTokenDTO;
import com.example.EBook_Management_BE.dtos.UpdateUserDTO;
import com.example.EBook_Management_BE.dtos.UserDTO;
import com.example.EBook_Management_BE.dtos.UserLoginDTO;
import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.entity.Token;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.InvalidPasswordException;
import com.example.EBook_Management_BE.mappers.UserMapper;
import com.example.EBook_Management_BE.responses.LoginResponse;
import com.example.EBook_Management_BE.responses.UserResponse;
import com.example.EBook_Management_BE.services.role.IRoleService;
import com.example.EBook_Management_BE.services.token.TokenService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.USER)
@RequiredArgsConstructor
public class UserController {
	private final IUserService userService;
	private final IUserRedisService userRedisService;
	private final IRoleService roleService;
	private final TokenService tokenService;
	
	private final LocalizationUtils localizationUtils;

	@Autowired
	private UserMapper userMapper;

	@PostMapping("/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
		if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
			throw new InvalidPasswordException(localizationUtils
					.getLocalizedMessage(MessageExceptionKeys.USER_PASSWORD_DIFFERENT_RETYPE_PASSWORD));
		}
		
		Role role = roleService.getRoleById(userDTO.getRoleId());
		
		User user = userMapper.mapToUserEntity(userDTO);
		user.setRole(role);
		
		User newUser = userService.createUser(user);
		userRedisService.saveUserById(newUser.getId(), newUser);
		
		UserResponse userResponse = userMapper.mapToUserResponse(newUser);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.USER_CREATE_SUCCESSFULLY))
				.data(userResponse)
				.build());
	}

	private boolean isMobileDevice(String userAgent) {
		// Kiểm tra User-Agent header để xác định thiết bị di động
		return userAgent.toLowerCase().contains("mobile");
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseObject> login(@Valid @RequestBody UserLoginDTO userLoginDTO,
			HttpServletRequest request) throws Exception {
		// Kiểm tra thông tin đăng nhập và sinh token
		String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
		
		String userAgent = request.getHeader("User-Agent");
		User userDetail = userService.getUserDetailsFromToken(token);
		Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

		LoginResponse loginResponse = LoginResponse.builder()
				.token(jwtToken.getToken()).tokenType(jwtToken.getTokenType()).refreshToken(jwtToken.getRefreshToken())
				.username(userDetail.getUsername())
				.roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
				.userId(userDetail.getId())
				.build();
		
		return ResponseEntity.ok().body(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.USER_LOGIN_SUCCESSFULLY))
				.data(loginResponse)
				.build());
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<ResponseObject> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO)
			throws Exception {
		User userDetail = userService.getUserDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());
				
		Token jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), userDetail);
		
		LoginResponse loginResponse = LoginResponse.builder()
				.token(jwtToken.getToken()).tokenType(jwtToken.getTokenType()).refreshToken(jwtToken.getRefreshToken())
				.username(userDetail.getUsername())
				.roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
				.userId(userDetail.getId())
				.build();
		
		return ResponseEntity.ok().body(ResponseObject.builder()
				.status(HttpStatus.OK)
				.data(loginResponse)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.TOKEN_REFRESH_SUCCESSFULLY))
				.build());
	}

	@PostMapping("/details")
	public ResponseEntity<ResponseObject> getUserDetails(@RequestHeader("Authorization") String authorizationHeader)
			throws Exception {
		String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
		User user = userService.getUserDetailsFromToken(extractedToken);

		UserResponse userResponse = userMapper.mapToUserResponse(user);
		
		return ResponseEntity.ok().body(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.USER_GET_DETAIL_SUCCESSFULLY))
				.data(userResponse)
				.build());
	}

	@PutMapping("/details/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateUserDetails(@PathVariable Long userId,
			@RequestBody UpdateUserDTO updatedUserDTO, @RequestHeader("Authorization") String authorizationHeader)
			throws Exception {
		if (!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
			throw new InvalidPasswordException(localizationUtils
					.getLocalizedMessage(MessageExceptionKeys.USER_PASSWORD_DIFFERENT_RETYPE_PASSWORD));
		}
		
		String extractedToken = authorizationHeader.substring(7);
		User user = userService.getUserDetailsFromToken(extractedToken);
				
		// Ensure that the user making the request matches the user being updated
		if (user.getId() != userId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		User updatedUser = userService.updateUser(userId, userMapper.mapToUserEntity(updatedUserDTO));
		userRedisService.saveUserById(userId, updatedUser);
		
		UserResponse userResponse = userMapper.mapToUserResponse(updatedUser);
		
		return ResponseEntity.ok().body(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.USER_UPDATE_SUCCESSFULLY))
				.data(userResponse)
				.build());
	}
	
	private String generatePassword() {
	    String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String specialChars = "!@#$%^&*()_+{}:<>?";

	    StringBuilder password = new StringBuilder();

	    // Add an uppercase letter
	    password.append(uppercaseChars.charAt((int) (Math.random() * uppercaseChars.length())));

	    // Generate remaining characters
	    for (int i = 0; i < 5; i++) {
	        int randomIndex = (int) (Math.random() * (uppercaseChars.length() + specialChars.length()));
	        if (randomIndex < uppercaseChars.length()) {
	            password.append(uppercaseChars.charAt(randomIndex));
	        } else {
	            password.append(specialChars.charAt(randomIndex - uppercaseChars.length()));
	        }
	    }

	    return password.toString();
	}
	
	@PutMapping("/reset-password/{phoneNumber}")
	public ResponseEntity<ResponseObject> resetPassword(@Valid @PathVariable String phoneNumber) throws DataNotFoundException {
			String newPassword = generatePassword(); // Tạo mật khẩu mới
			
			userService.resetPassword(phoneNumber, newPassword);
			
			return ResponseEntity.ok(ResponseObject.builder()
					.status(HttpStatus.OK)
					.message(localizationUtils.getLocalizedMessage(MessageKeys.USER_RESET_PASSWORD_SUCCESSFULLY))
					.data(newPassword)
					.build());
	}

	@PutMapping("/block/{userId}/{active}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseObject> blockOrEnable(@Valid @PathVariable long userId,
			@Valid @PathVariable short active) throws Exception {
		userService.blockOrEnable(userId, active);
		
		String message = active == 1
				? localizationUtils.getLocalizedMessage(MessageKeys.USER_ENABLE_SUCCESSFULLY) 
				: localizationUtils.getLocalizedMessage(MessageKeys.USER_BLOCK_SUCCESSFULLY);
		
		return ResponseEntity.ok()
				.body(ResponseObject.builder()
						.message(message)
						.status(HttpStatus.OK)
						.data(null)
						.build());
	}
}
