package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.dtos.FollowDTO;
import com.example.EBook_Management_BE.entity.Follow;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.repositories.FollowRepository;
import com.example.EBook_Management_BE.services.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService {
	private final FollowRepository followRepository;
	private final UserService userService;

	@Override
	@Transactional
	public Follow createFollow(FollowDTO followDTO) throws Exception {
		if (followDTO.getFollowing() == followDTO.getUserId()) {
			throw new Exception("Cannot follow yourself");
		}

		User user = userService.getUserById(followDTO.getUserId());

		Follow newFollow = Follow.builder().following(followDTO.getFollowing()).user(user).build();

		return followRepository.save(newFollow);
	}

	@Override
	public Follow getFollowById(Long followId) {
		return followRepository.findById(followId)
				.orElseThrow(() -> new RuntimeException(String.format("Follow with id = %d not found", followId)));
	}

	@Override
	@Transactional
	public void deleteFollow(Long followId) {
		followRepository.deleteById(followId);

	}

	@Override
	public Set<Follow> getAllFollowByUserId(Long userId) {
		User user = userService.getUserById(userId);
		
		return followRepository.findByUser(user);
	}

	@Override
	public Set<Follow> getAllFollowByFollowing(Long following) {
		return followRepository.findByFollowing(following);
	}

}
