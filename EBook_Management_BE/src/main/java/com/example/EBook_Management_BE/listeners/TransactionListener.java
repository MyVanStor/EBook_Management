package com.example.EBook_Management_BE.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.EBook_Management_BE.entity.Transaction;
import com.example.EBook_Management_BE.services.transaction.ITransactionRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionListener {
	private final ITransactionRedisService transactionRedisService;
	private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

	@PrePersist
	public void prePersist(Transaction transaction) {
		logger.info("prePersist");
	}

	@PostPersist // save = persis
	public void postPersist(Transaction transaction) {
		// Update Redis cache
		logger.info("postPersist");
		transactionRedisService.clearById(transaction.getId());
	}

	@PreUpdate
	public void preUpdate(Transaction transaction) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preUpdate");
	}

	@PostUpdate
	public void postUpdate(Transaction transaction) {
		// Update Redis cache
		logger.info("postUpdate");
		transactionRedisService.clearById(transaction.getId());
	}

	@PreRemove
	public void preRemove(Transaction transaction) {
		// ApplicationEventPublisher.instance().publishEvent(event);
		logger.info("preRemove");
	}

	@PostRemove
	public void postRemove(Transaction transaction) {
		// Update Redis cache
		logger.info("postRemove");
		transactionRedisService.clearById(transaction.getId());
	}
}
