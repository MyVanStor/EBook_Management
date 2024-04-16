package com.example.EBook_Management_BE.services.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRedisService implements IOrderRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));

	}

	private String getKeyFromId(Long id) {
		String key = String.format("Order: %d", id);

		return key;
	}

	@Override
	public Order getOrderById(Long orderId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(orderId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Order order = json != null ? redisObjectMapper.readValue(json, new TypeReference<Order>() {
		}) : null;
		return order;
	}

	@Override
	public void saveOrderById(Long orderId, Order order) throws Exception {
		String key = this.getKeyFromId(orderId);
		String json = redisObjectMapper.writeValueAsString(order);

		redisTemplate.opsForValue().set(key, json);
	}

}
