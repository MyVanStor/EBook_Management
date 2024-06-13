package com.example.EBook_Management_BE.services.rating;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Rating;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingRedisService implements IRatingRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Rating: %d", id);

		return key;
	}

	@Override
	public Rating getRatingById(Long ratingId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(ratingId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Rating rating = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Rating>() {
				})
				: null;
		return rating;
	}

	@Override
	public void saveRatingById(Long ratingId, Rating rating) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromId(ratingId);
		String json = redisObjectMapper.writeValueAsString(rating);
		redisTemplate.opsForValue().set(key, json);
	}
	
	private String getKeyFromBookId(Long bookId) {
		String key = String.format("Rating_all_by_book: %d", bookId);

		return key;
	}
	
	@Override
	public Set<Rating> getAllRatingBook(Long bookId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromBookId(bookId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Set<Rating> ratings = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Set<Rating>>() {
				})
				: null;
		return ratings;
	}

	@Override
	public void saveAllRatingBook(Long bookId, Set<Rating> ratings) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromBookId(bookId);
		String json = redisObjectMapper.writeValueAsString(ratings);
		
		redisTemplate.opsForValue().set(key, json);
		
	}

}
