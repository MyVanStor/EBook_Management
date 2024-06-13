package com.example.EBook_Management_BE.services.comment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Comment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentRedisService implements ICommentRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;
	
	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;
	
	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));		
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Comment: %d", id);
		
		return key;
	}
	
	@Override
	public Comment getCommentById(Long commentId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(commentId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Comment comment = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Comment>() {
				})
				: null;
		return comment;
	}

	@Override
	public void saveCommentById(Long commentId, Comment comment) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromId(commentId);
		String json = redisObjectMapper.writeValueAsString(comment);
		
		redisTemplate.opsForValue().set(key, json);
		
	}

}
