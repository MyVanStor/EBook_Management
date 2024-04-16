package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Order;
import com.example.EBook_Management_BE.services.order.IOrderRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderListener {
	private final IOrderRedisService orderRedisService;
	private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

	@PrePersist
	public void prePersist(Order order) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Order order) {
		// Update Redis cache
		logger.info("postPersist");
		orderRedisService.clearById(order.getId());
	}

	@PreUpdate
	public void preUpdate(Order order) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Order order) {
		// Update Redis cache
		logger.info("postUpdate");
		orderRedisService.clearById(order.getId());
	}

	@PreRemove
	public void preRemove(Order order) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Order order) {
		// Update Redis cache
		logger.info("postRemove");
		orderRedisService.clearById(order.getId());
	}
}
