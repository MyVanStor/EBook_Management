package com.example.EBook_Management_BE.services.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenRedisService implements ITokenRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clear(String tokenString) {
		redisTemplate.delete(getKey(tokenString));
	}

	private String getKey(String tokenString) {
		String key = String.format("Token: %s", tokenString);

		return key;
	}

	@Override
	public Token getToken(String tokenString) throws Exception {
		if (!useRedisCache) {
			return null;
		}
		String key = this.getKey(tokenString);
		String json = (String) redisTemplate.opsForValue().get(key);
		Token token = json != null ? redisObjectMapper.readValue(json, Token.class) : null;
		return token;
	}

	@Override
	public void saveToken(String tokenString, Token token) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKey(tokenString);
		String json = redisObjectMapper.writeValueAsString(token);
		redisTemplate.opsForValue().set(key, json);
	}

}
