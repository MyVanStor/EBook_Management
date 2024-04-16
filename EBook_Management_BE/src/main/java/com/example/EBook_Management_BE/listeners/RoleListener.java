package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.services.role.IRoleRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleListener {
	private final IRoleRedisService roleRedisService;
	private static final Logger logger = LoggerFactory.getLogger(RoleListener.class);

	@PrePersist
	public void prePersist(Role role) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Role role) {
		// Update Redis cache
		logger.info("postPersist");
		roleRedisService.clearById(role.getId());
	}

	@PreUpdate
	public void preUpdate(Role role) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Role role) {
		// Update Redis cache
		logger.info("postUpdate");
		roleRedisService.clearById(role.getId());
	}

	@PreRemove
	public void preRemove(Role role) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Role role) {
		// Update Redis cache
		logger.info("postRemove");
		roleRedisService.clearById(role.getId());
	}
}
