package com.example.EBook_Management_BE.services.role;

import com.example.EBook_Management_BE.entity.Role;

public interface IRoleRedisService {
void clearById(Long id);
	
	Role getRoleById(Long roleId) throws Exception;
	
	void saveRoleById(Long roleId, Role role) throws Exception;
}
