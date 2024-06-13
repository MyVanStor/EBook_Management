package com.example.EBook_Management_BE.services.role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.entity.Role;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleRedisService implements IRoleRedisService{
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisObjectMapper;

	@Value("${spring.data.redis.use-redis-cache}")
	private boolean useRedisCache;
	
	@Override
	public void clearById(Long id) {
		redisTemplate.delete(getKeyFromId(id));
	}

	private String getKeyFromId(Long id) {
		String key = String.format("Role: %d", id);
		
		return key;
	}
	
	@Override
	public Role getRoleById(Long roleId) throws Exception {
		if (!useRedisCache) {
			return null;
		}
		String key = this.getKeyFromId(roleId);
		String json = (String) redisTemplate.opsForValue().get(key);
		Role role = json != null
				? redisObjectMapper.readValue(json, new TypeReference<Role>() {
				})
				: null;
		return role;
	}

	@Override
	public void saveRoleById(Long roleId, Role role) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromId(roleId);
		String json = redisObjectMapper.writeValueAsString(role);
		
		redisTemplate.opsForValue().set(key, json);
	}

	private String getKeyFromName(String roleName) {
		String key = String.format("Role_name: %s", roleName);

		return key;
	}

	@Override
	public Role getRoleByName(String roleName) throws Exception {
		if (!useRedisCache) {
			return null;
		}

		String key = this.getKeyFromName(roleName);
		String json = (String) redisTemplate.opsForValue().get(key);

        return json != null
				? redisObjectMapper.readValue(json, new TypeReference<Role>() {
				})
				: null;
    }

	@Override
	public void saveRoleByName(String roleName, Role role) throws Exception {
		if (useRedisCache == false) {
			return;
		}
		String key = this.getKeyFromName(roleName);
		String json = redisObjectMapper.writeValueAsString(role);

		redisTemplate.opsForValue().set(key, json);
	}

}
