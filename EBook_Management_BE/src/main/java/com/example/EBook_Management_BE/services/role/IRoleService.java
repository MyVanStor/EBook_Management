package com.example.EBook_Management_BE.services.role;

import com.example.EBook_Management_BE.dtos.RoleDTO;
import com.example.EBook_Management_BE.entity.Role;

public interface IRoleService {
	Role createRole(RoleDTO roleDTO);

	Role getRoleById(Long roleId);

	Role updateRole(Long roleId, RoleDTO roleDTO);

	Role deleteRoleById(Long roleId) throws Exception;
}
