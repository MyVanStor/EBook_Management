package com.example.EBook_Management_BE.services.author;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Author;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
		redisTemplate.delete("Author: all");
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

	private String getKeyFrom() {
		String key = "Author: all";

		return key;
	}

	@Override
	public List<Author> getAllAuthors() throws Exception {
		if (!useRedisCache) {
			return null;
		}

		String key = this.getKeyFrom();
		String json = (String) redisTemplate.opsForValue().get(key);
		List<Author> authors = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<Author>>() {
		}) : null;

		return authors;
	}

	@Override
	public void saveAllAuthors(List<Author> authors) throws Exception {
		String key = this.getKeyFrom();
		String json = redisObjectMapper.writeValueAsString(authors);
		redisTemplate.opsForValue().set(key, json);
	}

}
