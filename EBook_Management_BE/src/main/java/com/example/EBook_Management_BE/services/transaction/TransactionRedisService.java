package com.example.EBook_Management_BE.services.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionRedisService implements ITransactionRedisService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;

	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Transaction: %d", id);

		return key;
	}

	@Override
	public Transaction getTransactionById(Long transactionId) throws Exception {
		if (!useRedisCache) {
			return null;
		}
		String key = this.getKeyFromId(transactionId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Transaction transaction = json != null ? redisObjectMapper.readValue(json, Transaction.class) : null;
		return transaction;
	}

	@Override
	public void saveTransactionById(Long transactionId, Transaction transaction) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromId(transactionId);
		String json = redisObjectMapper.writeValueAsString(transaction);
		redisTemplate.opsForValue().set(key, json);
	}

}
