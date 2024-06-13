package com.example.EBook_Management_BE.services.book;

import com.example.EBook_Management_BE.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookRedisService implements IBookRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clear(Book book) {
		redisTemplate.delete(getKeyFromId(book.getId()));
		redisTemplate.delete("book: all**");
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
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromId(bookId);
		String json = redisObjectMapper.writeValueAsString(book);
		
		redisTemplate.opsForValue().set(key, json);
	}

	private String getKeyFromUser(Long userId) {
		String key = String.format("Book: all - User : %d", userId);

		return key;
	}

	@Override
	public List<Book> getAllBookByUser(User user) throws JsonProcessingException {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromUser(user.getId());
		String json = (String) redisTemplate.opsForValue().get(key);
		List<Book> books = json != null
				? redisObjectMapper.readValue(json, new TypeReference<List<Book>>() {
		})
				: null;
		return books;
	}

	@Override
	public void saveAllBookByUser(List<Book> books, User user) throws JsonProcessingException {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromUser(user.getId());
		String json = redisObjectMapper.writeValueAsString(books);

		redisTemplate.opsForValue().set(key, json);
	}

}
