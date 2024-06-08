package com.example.EBook_Management_BE.services.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
		redisTemplate.delete("Category: all");
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

	private String getKeyFrom() {
		String key = "Category: all";

		return key;
	}

	@Override
	public List<Category> getAllCategory() throws JsonProcessingException {
		if (!useRedisCache) {
			return null;
		}
		String key = this.getKeyFrom();
		String json = (String) redisTemplate.opsForValue().get(key);
		List<Category> categories = json != null
				? redisObjectMapper.readValue(json, new TypeReference<List<Category>>() {
		})
				: null;
		return categories;
	}

	@Override
	public void saveAllCategory(List<Category> categories) throws JsonProcessingException {
		String key = this.getKeyFrom();
		String json = redisObjectMapper.writeValueAsString(categories);

		redisTemplate.opsForValue().set(key, json);
	}

}
