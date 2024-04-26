package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import com.example.EBook_Management_BE.entity.Follow;

public interface IFollowService {
	Follow createFollow(Follow follow) throws Exception;
	
	Follow getFollowById(Long followId) throws Exception;
	
	void deleteFollow(Long followId);
	
	Set<Follow> getAllFollowByUserId(Long userId) throws Exception;
	
	Set<Follow> getAllFollowByFollowing(Long following);
}
