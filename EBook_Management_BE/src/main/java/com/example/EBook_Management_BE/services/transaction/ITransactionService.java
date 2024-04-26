package com.example.EBook_Management_BE.services.transaction;

import com.example.EBook_Management_BE.entity.Transaction;

public interface ITransactionService {
	Transaction createTransaction(Transaction transaction);
	
	Transaction getTransactionById(Long transactionId) throws Exception;
	
	Transaction updateTransaction(Long transactionId, Transaction transaction) throws Exception;
	
	void deleteTransaction(Long transactionId) throws Exception;
}
