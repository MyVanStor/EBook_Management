package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Follow;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.SelfFollowException;
import com.example.EBook_Management_BE.repositories.FollowRepository;
import com.example.EBook_Management_BE.services.user.UserService;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService {
	private final FollowRepository followRepository;
	private final IFollowRedisService followRedisService;
	private final UserService userService;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Follow createFollow(Follow follow) throws Exception {
		if (follow.getFollowing() == follow.getUser().getId()) {
			throw new SelfFollowException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.FOLLOW_NOT_FOLLOW_YOURSELF));
		}

		return followRepository.save(follow);
	}

	@Override
	public Follow getFollowById(Long followId) throws Exception {
		Follow follow = followRedisService.getFollowById(followId);
		if (follow == null) {
			follow = followRepository.findById(followId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.FOLLOW_NOT_FOUND)));
			
			followRedisService.saveFollowById(followId, follow);
		}
		return follow;
	}

	@Override
	@Transactional
	public void deleteFollow(Long followId) {
		followRepository.deleteById(followId);
	}

	@Override
	public Set<Follow> getAllFollowByUserId(Long userId) throws Exception {
		User user = userService.getUserById(userId);

		return followRepository.findByUser(user);
	}

	@Override
	public Set<Follow> getAllFollowByFollowing(Long following) {
		return followRepository.findByFollowing(following);
	}

}
