package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Author;
import com.example.EBook_Management_BE.services.author.IAuthorRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorListener {
	private final IAuthorRedisService authorRedisService;
	private static final Logger logger = LoggerFactory.getLogger(AuthorListener.class);

	@PrePersist
	public void prePersist(Author author) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Author author) {
		// Update Redis cache
		logger.info("postPersist");
		authorRedisService.clearById(author.getId());
	}

	@PreUpdate
	public void preUpdate(Author author) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Author author) {
		// Update Redis cache
		logger.info("postUpdate");
		authorRedisService.clearById(author.getId());
	}

	@PreRemove
	public void preRemove(Author author) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Author author) {
		// Update Redis cache
		logger.info("postRemove");
		authorRedisService.clearById(author.getId());
	}
}
