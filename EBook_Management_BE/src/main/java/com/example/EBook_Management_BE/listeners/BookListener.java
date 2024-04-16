package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Book;
import com.example.EBook_Management_BE.services.book.IBookRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookListener {
	private final IBookRedisService bookRedisService;
	private static final Logger logger = LoggerFactory.getLogger(BookListener.class);

	@PrePersist
	public void prePersist(Book book) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Book book) {
		// Update Redis cache
		logger.info("postPersist");
		bookRedisService.clearById(book.getId());
	}

	@PreUpdate
	public void preUpdate(Book book) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Book book) {
		// Update Redis cache
		logger.info("postUpdate");
		bookRedisService.clearById(book.getId());
	}

	@PreRemove
	public void preRemove(Book book) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Book book) {
		// Update Redis cache
		logger.info("postRemove");
		bookRedisService.clearById(book.getId());
	}
}
