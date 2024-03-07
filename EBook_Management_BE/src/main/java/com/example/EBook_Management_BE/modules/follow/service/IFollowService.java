package com.example.EBook_Management_BE.modules.follow.service;

import java.util.Set;

import com.example.EBook_Management_BE.common.entity.Follow;
import com.example.EBook_Management_BE.modules.follow.dto.FollowDTO;

public interface IFollowService {
	Follow createFollow(FollowDTO followDTO) throws Exception;
	
	Follow getFollowById(Long followId);
	
	void deleteFollow(Long followId);
	
	Set<Follow> getAllFollowByUserId(Long userId);
	
	Set<Follow> getAllFollowByFollowing(Long following);
}
