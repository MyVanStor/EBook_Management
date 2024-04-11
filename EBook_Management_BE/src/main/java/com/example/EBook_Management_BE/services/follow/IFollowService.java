package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import com.example.EBook_Management_BE.dtos.FollowDTO;
import com.example.EBook_Management_BE.entity.Follow;

public interface IFollowService {
	Follow createFollow(FollowDTO followDTO) throws Exception;
	
	Follow getFollowById(Long followId);
	
	void deleteFollow(Long followId);
	
	Set<Follow> getAllFollowByUserId(Long userId);
	
	Set<Follow> getAllFollowByFollowing(Long following);
}
