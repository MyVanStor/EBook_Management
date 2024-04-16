package com.example.EBook_Management_BE.services.author;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Author;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorRedisService implements IAuthorRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Author: %d", id);

		return key;
	}

	@Override
	public Author getAuthorById(Long authorId) throws Exception {
		if (!useRedisCache) {
			return null;
		}
		String key = this.getKeyFromId(authorId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Author author = json != null ? redisObjectMapper.readValue(json, Author.class) : null;
		return author;
	}

	@Override
	public void saveAuthorById(Long authorId, Author author) throws Exception {
		String key = this.getKeyFromId(authorId);
		String json = redisObjectMapper.writeValueAsString(author);
		redisTemplate.opsForValue().set(key, json);
	}

}
