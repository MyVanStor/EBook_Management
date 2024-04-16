package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Follow;
import com.example.EBook_Management_BE.services.follow.IFollowRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FollowListener {
	private final IFollowRedisService followRedisService;
	private static final Logger logger = LoggerFactory.getLogger(FollowListener.class);

	@PrePersist
	public void prePersist(Follow follow) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Follow follow) {
		// Update Redis cache
		logger.info("postPersist");
		followRedisService.clearById(follow.getId());
	}

	@PreUpdate
	public void preUpdate(Follow follow) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Follow follow) {
		// Update Redis cache
		logger.info("postUpdate");
		followRedisService.clearById(follow.getId());
	}

	@PreRemove
	public void preRemove(Follow follow) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Follow follow) {
		// Update Redis cache
		logger.info("postRemove");
		followRedisService.clearById(follow.getId());
	}
}
