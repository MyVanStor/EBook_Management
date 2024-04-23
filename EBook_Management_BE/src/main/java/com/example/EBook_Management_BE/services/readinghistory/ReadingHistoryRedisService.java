package com.example.EBook_Management_BE.services.readinghistory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.ReadingHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadingHistoryRedisService implements IReadingHistoryRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));

	}

	private String getKeyFromId(Long id) {
		String key = String.format("Reading-history: %d", id);

		return key;
	}

	@Override
	public ReadingHistory getReadingHistoryById(Long id) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(id);
		String json = (String) redisTemplate.opsForValue().get(key);
		ReadingHistory readingHistory = json != null
				? redisObjectMapper.readValue(json, new TypeReference<ReadingHistory>() {
				})
				: null;
		return readingHistory;
	}

	@Override
	public void saveReadingHistoryById(Long id, ReadingHistory readingHistory) throws Exception {
		String key = this.getKeyFromId(id);
		String json = redisObjectMapper.writeValueAsString(readingHistory);
		
		redisTemplate.opsForValue().set(key, json);
	}

}
