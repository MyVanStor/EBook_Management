package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Category;
import com.example.EBook_Management_BE.services.category.ICategoryRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryListener {
	private final ICategoryRedisService categoryRedisService;
	private static final Logger logger = LoggerFactory.getLogger(CategoryListener.class);

	@PrePersist
	public void prePersist(Category category) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Category category) {
		// Update Redis cache
		logger.info("postPersist");
		categoryRedisService.clearById(category.getId());
	}

	@PreUpdate
	public void preUpdate(Category category) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Category category) {
		// Update Redis cache
		logger.info("postUpdate");
		categoryRedisService.clearById(category.getId());
	}

	@PreRemove
	public void preRemove(Category category) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Category category) {
		// Update Redis cache
		logger.info("postRemove");
		categoryRedisService.clearById(category.getId());
	}
}
