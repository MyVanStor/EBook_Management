package com.example.EBook_Management_BE.services.follow;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Follow;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowRedisService implements IFollowRedisService{
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;
	
	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;
	
	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));				
	}
	
	private String getKeyFromId(Long id) {
		String key = String.format("Follow: %d", id);
		
		return key;
	}
	
	@Override
	public Follow getFollowById(Long followId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(followId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Follow follow = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Follow>() {
				})
				: null;
		return follow;
	}

	@Override
	public void saveFollowById(Long followId, Follow follow) throws Exception {
		String key = this.getKeyFromId(followId);
		String json = redisObjectMapper.writeValueAsString(follow);
		
		redisTemplate.opsForValue().set(key, json);
	}
	
	private String getKeyFromGetBy(String getBy, Long id) {
		String key = String.format("Follow_get_by: %s:%d", getBy, id);
		
		return key;
	}

	@Override
	public Set<Follow> getAllFollow(String getBy, Long id) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromGetBy(getBy, id);
		String json = (String) redisTemplate.opsForValue().get(key);
		Set<Follow> follows = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Set<Follow>>() {
				})
				: null;
		return follows;
	}

	@Override
	public void saveAllFollow(String getBy, Long id, Set<Follow> follows) throws Exception {
		String key = this.getKeyFromGetBy(getBy, id);
		String json = redisObjectMapper.writeValueAsString(follows);
		
		redisTemplate.opsForValue().set(key, json);
	}
	
}
