package com.example.EBook_Management_BE.services.transaction;

import com.example.EBook_Management_BE.entity.Transaction;

public interface ITransactionRedisService {
	void clearById(Long id);

	Transaction getTransactionById(Long transactionId) throws Exception;

	void saveTransactionById(Long transactionId, Transaction transaction) throws Exception;
}
