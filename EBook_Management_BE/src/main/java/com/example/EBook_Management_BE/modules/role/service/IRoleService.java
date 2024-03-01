package com.example.EBook_Management_BE.modules.role.service;

import com.example.EBook_Management_BE.common.entity.Role;
import com.example.EBook_Management_BE.modules.role.dto.RoleDTO;

public interface IRoleService {
	Role createRole(RoleDTO roleDTO);

	Role getRoleById(Long roleId);

	Role updateRole(Long roleId, RoleDTO roleDTO);

	Role deleteRoleById(Long roleId) throws Exception;
}
