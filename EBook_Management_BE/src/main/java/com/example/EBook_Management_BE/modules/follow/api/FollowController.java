package com.example.EBook_Management_BE.modules.follow.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.common.components.LocalizationUtils;
import com.example.EBook_Management_BE.common.entity.Follow;
import com.example.EBook_Management_BE.common.enums.Uri;
import com.example.EBook_Management_BE.common.utils.MessageKeys;
import com.example.EBook_Management_BE.common.utils.ResponseObject;
import com.example.EBook_Management_BE.modules.follow.dto.FollowDTO;
import com.example.EBook_Management_BE.modules.follow.response.FollowResponse;
import com.example.EBook_Management_BE.modules.follow.service.FollowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.FOLLOW)
@Validated
@RequiredArgsConstructor
public class FollowController {
	private final FollowService followService;
	private final LocalizationUtils localizationUtils;

	@PostMapping("")
	public ResponseEntity<FollowResponse> createFollow(@Valid @RequestBody FollowDTO followDTO, BindingResult result)
			throws Exception {
		FollowResponse followResponse = new FollowResponse();

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

			followResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_FOLLOW_SUCCESSFULLY));
			followResponse.setErrors(errorMessages);

			return ResponseEntity.badRequest().body(followResponse);
		}

		Follow follow = followService.createFollow(followDTO);
		followResponse.setFollow(follow);

		return ResponseEntity.created(null).body(followResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getFollowById(@PathVariable Long id) {
		Follow existingFollow = followService.getFollowById(id);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.data(existingFollow)
				.message("Get follow information successfully")
				.status(HttpStatus.OK)
				.build());
	}
	
	@GetMapping("/user/all/{id}")
	public ResponseEntity<ResponseObject> getAllFollowByUserId(@PathVariable Long id) {
		Set<Follow> follows = followService.getAllFollowByUserId(id);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.data(follows)
				.message(String.format("Get all user is follow by user have id = %d successfully", id))
				.status(HttpStatus.OK)
				.build());
	}
	
	@GetMapping("/following/all/{id}")
	public ResponseEntity<ResponseObject> getAllFollowByFollowing(@PathVariable Long id) {
		Set<Follow> follows = followService.getAllFollowByFollowing(id);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.data(follows)
				.message(String.format("Get all user is follow by following have id = %d successfully", id))
				.status(HttpStatus.OK)
				.build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject> deleteFollow(@PathVariable Long id) throws Exception {
		followService.deleteFollow(id);
		return ResponseEntity.ok(ResponseObject.builder().data(null)
				.message(String.format("Follow with id = %d deleted successfully", id)).status(HttpStatus.OK).build());
	}
}
