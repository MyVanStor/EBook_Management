package com.example.EBook_Management_BE.services.painter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Painter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PainterRedisService implements IPainterRedisService{
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;
	
	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
		redisTemplate.delete("Painter: all");
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Painter: %d", id);
		
		return key;
	}

	@Override
	public Painter getPainterById(Long painterId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(painterId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Painter painter = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Painter>() {
				})
				: null;
		return painter;
	}

	@Override
	public void savePainterById(Long painterId, Painter painter) throws Exception {
		String key = this.getKeyFromId(painterId);
		String json = redisObjectMapper.writeValueAsString(painter);
		
		redisTemplate.opsForValue().set(key, json);
	}

	private String getKeyFrom() {
		String key = "Painter: all";

		return key;
	}

	@Override
	public List<Painter> getAllPainter() throws JsonProcessingException {
		if (useRedisCache == false) {
			return null;
		}

		String key = this.getKeyFrom();
		String json = (String) redisTemplate.opsForValue().get(key);
		List<Painter> painters = json != null
				? redisObjectMapper.readValue(json, new TypeReference<List<Painter>>() {
		})
				: null;
		return painters;
	}

	@Override
	public void saveAllPainter(List<Painter> painters) throws JsonProcessingException {
		String key = this.getKeyFrom();
		String json = redisObjectMapper.writeValueAsString(painters);

		redisTemplate.opsForValue().set(key, json);
	}

}
