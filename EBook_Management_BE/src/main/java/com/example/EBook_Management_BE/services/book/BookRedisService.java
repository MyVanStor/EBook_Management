package com.example.EBook_Management_BE.services.book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookRedisService implements IBookRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Book: %d", id);

		return key;
	}

	@Override
	public Book getBookById(Long bookId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(bookId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Book book = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Book>() {
				})
				: null;
		return book;
	}

	@Override
	public void saveBookById(Long bookId, Book book) throws Exception {
		String key = this.getKeyFromId(bookId);
		String json = redisObjectMapper.writeValueAsString(book);
		
		redisTemplate.opsForValue().set(key, json);
	}

}
