package com.example.EBook_Management_BE.services.transaction;

import com.example.EBook_Management_BE.entity.Transaction;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;

public interface ITransactionService {
	Transaction createTransaction(Transaction transaction);
	
	Transaction getTransactionById(Long transactionId) throws DataNotFoundException;
	
	Transaction updateTransaction(Long transactionId, Transaction transaction) throws Exception;
	
	void deleteTransaction(Long transactionId) throws Exception;
}
