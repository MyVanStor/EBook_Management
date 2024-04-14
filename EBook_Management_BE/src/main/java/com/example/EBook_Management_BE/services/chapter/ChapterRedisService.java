package com.example.EBook_Management_BE.services.chapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Chapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterRedisService implements IChapterRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Chapter: %d", id);

		return key;
	}

	@Override
	public Chapter getChapterById(Long chapterId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(chapterId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Chapter chapter = json != null ? redisObjectMapper.readValue(json, new TypeReference<Chapter>() {
		}) : null;
		return chapter;
	}

	@Override
	public void saveChapterById(Long chapterId, Chapter chapter) throws Exception {
		String key = this.getKeyFromId(chapterId);
		String json = redisObjectMapper.writeValueAsString(chapter);

		redisTemplate.opsForValue().set(key, json);

	}
}
