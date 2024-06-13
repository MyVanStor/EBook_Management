package com.example.EBook_Management_BE.services.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRedisService implements IUserRedisService{
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;
	
	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
		redisTemplate.delete("*");
	}
	
	private String getKeyFromId(Long id) {
		String key = String.format("User: %d", id);
		
		return key;
	}
	
	@Override
	public User getUserById(Long userId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(userId);
		String json = (String) redisTemplate.opsForValue().get(key);
		User user = json != null
				? redisObjectMapper.readValue(json, new TypeReference<User>() {
				})
				: null;
		return user;
	}

	@Override
	public void saveUserById(Long userId, User user) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromId(userId);
		String json = redisObjectMapper.writeValueAsString(user);
		
		redisTemplate.opsForValue().set(key, json);
	}
	
	private String getKeyFromTokenOrRefreshToken(String token) {
		String key = String.format("User_token: %s", token);
		
		return key;
	}

	@Override
	public User getUserByTokenOrRefreshToken(String token) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = getKeyFromTokenOrRefreshToken(token);
		String json = (String) redisTemplate.opsForValue().get(key);
		User user = json != null
				? redisObjectMapper.readValue(json, new TypeReference<User>() {
				})
				: null;
		return user;
	}

	@Override
	public void saveUserByTokenOrRefreshToken(String token, User user) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromTokenOrRefreshToken(token);
		String json = redisObjectMapper.writeValueAsString(user);
		
		redisTemplate.opsForValue().set(key, json);
	}
	
	private String getKeyFromPhoneNumber(String phoneNumber) {
		String key = String.format("User_phone_number: %s", phoneNumber);
		
		return key;
	}
	
	@Override
	public User getUserByPhoneNumber(String phoneNumber) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = getKeyFromPhoneNumber(phoneNumber);
		String json = (String) redisTemplate.opsForValue().get(key);
		User user = json != null
				? redisObjectMapper.readValue(json, new TypeReference<User>() {
				})
				: null;
		return user;
	}

	@Override
	public void saveUserByPhoneNumber(String phoneNumber, User user) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromPhoneNumber(phoneNumber);
		String json = redisObjectMapper.writeValueAsString(user);
		
		redisTemplate.opsForValue().set(key, json);
	}

}
