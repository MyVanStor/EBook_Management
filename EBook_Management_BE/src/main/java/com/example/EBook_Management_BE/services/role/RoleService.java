package com.example.EBook_Management_BE.services.role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.exceptions.DeleteException;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.exceptions.DuplicateException;
import com.example.EBook_Management_BE.repositories.RoleRepository;
import com.example.EBook_Management_BE.repositories.UserRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
	private final RoleRepository roleRepository;
	private final IRoleRedisService roleRedisService;
	private final UserRepository userRepository;

	private final LocalizationUtils localizationUtils;

	@Override
	@Transactional
	public Role createRole(Role role) throws DuplicateException {
		if (roleRepository.existsByName(role.getName())) {
			throw new DuplicateException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.ROLE_DUPLICATE_NAME));
		}

		return roleRepository.save(role);
	}

	@Override
	public Role getRoleById(Long roleId) throws Exception {
		Role role = roleRedisService.getRoleById(roleId);
		if (role == null) {
			role = roleRepository.findById(roleId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.ROLE_NOT_FOUND)));
			
			roleRedisService.saveRoleById(roleId, role);
		}
		return role;
	}

	@Override
	public Role getRoleByName(String roleName) throws Exception {
		Role role = roleRedisService.getRoleByName(roleName);
		if (role == null) {
			role = roleRepository.findByName(roleName);

			roleRedisService.saveRoleByName(roleName, role);
		}

		return role;
	}

	@Override
	@Transactional
	public Role updateRole(Long roleId, Role roleUpdate) throws Exception {
		Role exitstingRole = getRoleById(roleId);

		roleUpdate.setId(exitstingRole.getId());
		roleRepository.save(roleUpdate);

		return roleUpdate;
	}

	@Override
	@Transactional
	public void deleteRoleById(Long roleId) throws Exception {
		Role role = getRoleById(roleId);

		if (userRepository.existsByRole(role)) {
			throw new DeleteException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.ROLE_DELETE_HAVE_ASSOCIATED_USER));
		} else {
			roleRepository.deleteById(roleId);
		}
	}

}