package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Rating;
import com.example.EBook_Management_BE.services.rating.IRatingRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatingListener {
	private final IRatingRedisService ratingRedisService;
	private static final Logger logger = LoggerFactory.getLogger(RatingListener.class);

	@PrePersist
	public void prePersist(Rating rating) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Rating rating) {
		// Update Redis cache
		logger.info("postPersist");
		ratingRedisService.clearById(rating.getId());
	}

	@PreUpdate
	public void preUpdate(Rating rating) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Rating rating) {
		// Update Redis cache
		logger.info("postUpdate");
		ratingRedisService.clearById(rating.getId());
	}

	@PreRemove
	public void preRemove(Rating rating) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Rating rating) {
		// Update Redis cache
		logger.info("postRemove");
		ratingRedisService.clearById(rating.getId());
	}
}
