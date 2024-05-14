package com.example.EBook_Management_BE.services.role;

import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.exceptions.DuplicateException;

public interface IRoleService {
	Role createRole(Role role) throws DuplicateException;

	Role getRoleById(Long roleId)  throws Exception;

	Role getRoleByName(String roleName)  throws Exception;

	Role updateRole(Long roleId, Role roleUpdate) throws Exception;

	void deleteRoleById(Long roleId) throws Exception;
}
