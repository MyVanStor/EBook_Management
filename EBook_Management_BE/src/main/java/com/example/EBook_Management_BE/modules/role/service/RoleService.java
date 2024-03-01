package com.example.EBook_Management_BE.modules.role.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.common.entity.Role;
import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.common.repository.RoleRepository;
import com.example.EBook_Management_BE.common.repository.UserRepository;
import com.example.EBook_Management_BE.modules.role.dto.RoleDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public Role createRole(RoleDTO roleDTO) {
		Role newRole = Role.builder().name(roleDTO.getName()).build();

		return roleRepository.save(newRole);
	}

	@Override
	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
	}

	@Override
	@Transactional
	public Role updateRole(Long roleId, RoleDTO roleDTO) {
		Role existingRole = getRoleById(roleId);

		existingRole.setName(roleDTO.getName());
		roleRepository.save(existingRole);

		return existingRole;
	}

	@Override
	@Transactional
	public Role deleteRoleById(Long roleId) throws Exception {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

		List<User> users = userRepository.findByRole(role);
		if (!users.isEmpty()) {
			throw new IllegalStateException("Cannot delete role with associated user");
		} else {
			roleRepository.deleteById(roleId);
			return role;
		}
	}

}
