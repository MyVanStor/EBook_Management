package com.example.EBook_Management_BE.services.orderdetail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.OrderDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailRedisService implements IOrderDetailRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Order_detail: %d", id);

		return key;
	}

	@Override
	public OrderDetail getOrderDetailById(Long orderDetailId) throws Exception {
		if (useRedisCache == false) {
			return null;
		}
		String key = this.getKeyFromId(orderDetailId);
		String json = (String) redisTemplate.opsForValue().get(key);
		OrderDetail orderDetail = json != null ? redisObjectMapper.readValue(json, new TypeReference<OrderDetail>() {
		}) : null;
		return orderDetail;
	}

	@Override
	public void saveOrderDetailById(Long orderDetailId, OrderDetail orderDetail) throws Exception {
		String key = this.getKeyFromId(orderDetailId);
		String json = redisObjectMapper.writeValueAsString(orderDetail);

		redisTemplate.opsForValue().set(key, json);
	}

}
