package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Painter;
import com.example.EBook_Management_BE.services.painter.IPainterRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PainterListener {
	private final IPainterRedisService painterRedisService;
	private static final Logger logger = LoggerFactory.getLogger(PainterListener.class);

	@PrePersist
	public void prePersist(Painter painter) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Painter painter) {
		// Update Redis cache
		logger.info("postPersist");
		painterRedisService.clearById(painter.getId());
	}

	@PreUpdate
	public void preUpdate(Painter painter) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Painter painter) {
		// Update Redis cache
		logger.info("postUpdate");
		painterRedisService.clearById(painter.getId());
	}

	@PreRemove
	public void preRemove(Painter painter) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Painter painter) {
		// Update Redis cache
		logger.info("postRemove");
		painterRedisService.clearById(painter.getId());
	}
}
