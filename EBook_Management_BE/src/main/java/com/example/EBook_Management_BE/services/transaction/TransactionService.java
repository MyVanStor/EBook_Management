package com.example.EBook_Management_BE.services.transaction;

import org.springframework.stereotype.Service;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.entity.Transaction;
import com.example.EBook_Management_BE.exceptions.DataNotFoundException;
import com.example.EBook_Management_BE.repositories.TransactionRepository;
import com.example.EBook_Management_BE.utils.MessageExceptionKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
	private final TransactionRepository transactionRepository;
	private final ITransactionRedisService transactionRedisService;

	private final LocalizationUtils localizationUtils;

	@Override
	public Transaction createTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction getTransactionById(Long transactionId) throws Exception {
		Transaction transaction = transactionRedisService.getTransactionById(transactionId);
		if (transaction == null) {
			transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new DataNotFoundException(
					localizationUtils.getLocalizedMessage(MessageExceptionKeys.TRANSACTION_NOT_FOUND)));
			
			transactionRedisService.saveTransactionById(transactionId, transaction);
		}
		return transaction;
	}

	@Override
	public Transaction updateTransaction(Long transactionId, Transaction transaction) throws Exception {
		Transaction existingTransaction = getTransactionById(transactionId);

		transaction.setId(existingTransaction.getId());
		transactionRepository.save(transaction);

		return transaction;
	}

	@Override
	public void deleteTransaction(Long transactionId) throws Exception {
		// TODO Auto-generated method stub

	}

}
