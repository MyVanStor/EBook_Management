package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import com.example.EBook_Management_BE.entity.Follow;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface IFollowService {
	Follow createFollow(Follow follow) throws Exception;
	
	Follow getFollowById(Long followId) throws DataNotFoundException;
	
	void deleteFollow(Long followId);
	
	Set<Follow> getAllFollowByUserId(Long userId) throws DataNotFoundException;
	
	Set<Follow> getAllFollowByFollowing(Long following);
}
