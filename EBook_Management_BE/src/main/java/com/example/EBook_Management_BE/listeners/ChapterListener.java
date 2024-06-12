package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Chapter;
import com.example.EBook_Management_BE.services.chapter.IChapterRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChapterListener {
	private final IChapterRedisService chapterRedisService;
	private static final Logger logger = LoggerFactory.getLogger(ChapterListener.class);

	@PrePersist
	public void prePersist(Chapter chapter) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Chapter chapter) {
		// Update Redis cache
		logger.info("postPersist");
		chapterRedisService.clearById(chapter);
	}

	@PreUpdate
	public void preUpdate(Chapter chapter) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Chapter chapter) {
		// Update Redis cache
		logger.info("postUpdate");
		chapterRedisService.clearById(chapter);
	}

	@PreRemove
	public void preRemove(Chapter chapter) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Chapter chapter) {
		// Update Redis cache
		logger.info("postRemove");
		chapterRedisService.clearById(chapter);
	}
}
