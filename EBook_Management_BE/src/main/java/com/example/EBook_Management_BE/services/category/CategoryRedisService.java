package com.example.EBook_Management_BE.services.category;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryRedisService implements ICategoryRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Category: %d", id);

		return key;
	}

	@Override
	public Category getCategoryById(Long categoryId) throws Exception {
		if (!useRedisCache) {
			return null;
		}
		String key = this.getKeyFromId(categoryId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Category category = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Category>() {
				})
				: null;
		return category;
	}

	@Override
	public void saveCategoryById(Long categoryId, Category category) throws Exception {
		String key = this.getKeyFromId(categoryId);
		String json = redisObjectMapper.writeValueAsString(category);
		
		redisTemplate.opsForValue().set(key, json);
	}

}
