package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import com.example.EBook_Management_BE.entity.Follow;

public interface IFollowRedisService {
	void clearById(Long id);

	Follow getFollowById(Long followId) throws Exception;

	void saveFollowById(Long followId, Follow follow) throws Exception;
	
	Set<Follow> getAllFollow(String typeGet, Long id) throws Exception;
	
	void saveAllFollow(String typeGet, Long id, Set<Follow> follows) throws Exception;
}
