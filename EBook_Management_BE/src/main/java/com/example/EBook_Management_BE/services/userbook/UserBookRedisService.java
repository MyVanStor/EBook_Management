package com.example.EBook_Management_BE.services.userbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.UserBook;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookRedisService implements IUserBookRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("UserBook: %d", id);

		return key;
	}

	@Override
	public UserBook getUserBookById(Long userBookId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(userBookId);
		String json = (String) redisTemplate.opsForValue().get(key);
		UserBook userBook = json != null ? redisObjectMapper.readValue(json, new TypeReference<UserBook>() {
		}) : null;
		return userBook;
	}

	@Override
	public void saveUserBookById(Long userBookId, UserBook author) throws Exception {
		String key = this.getKeyFromId(userBookId);
		String json = redisObjectMapper.writeValueAsString(author);

		redisTemplate.opsForValue().set(key, json);
	}

}
