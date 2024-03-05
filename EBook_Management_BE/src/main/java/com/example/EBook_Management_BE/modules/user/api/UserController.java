package com.example.EBook_Management_BE.modules.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.enums.Uri;
import com.example.EBook_Management_BE.common.utils.ResponseObject;
import com.example.EBook_Management_BE.modules.user.dto.UserDTO;
import com.example.EBook_Management_BE.modules.user.mapper.UserMapper;
import com.example.EBook_Management_BE.modules.user.response.UserResponse;
import com.example.EBook_Management_BE.modules.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.USER)
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping("/register")
	// can we register an "admin" user ?
	public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result)
			throws Exception {
		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			return ResponseEntity.badRequest().body(ResponseObject.builder().status(HttpStatus.BAD_REQUEST).data(null)
					.message(errorMessages.toString()).build());
		}

//        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
//            //registerResponse.setMessage();
//            return ResponseEntity.badRequest().body(ResponseObject.builder()
//                    .status(HttpStatus.BAD_REQUEST)
//                    .data(null)
//                    .message(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
//                    .build());
//        }

		User user = userService.createUser(userDTO);
		UserResponse userResponse = userMapper.mapToUserResponse(user);
		userResponse.setRoleName(user.getRole().getName());

		return ResponseEntity.created(null)
				.body(ResponseObject.builder().status(HttpStatus.CREATED)
						.data(userResponse)
						.message("Đăng ký tài khoản thành công").build());
	}
}
