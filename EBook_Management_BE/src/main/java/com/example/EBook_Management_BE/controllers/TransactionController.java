package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.TransactionDTO;
import com.example.EBook_Management_BE.entity.Transaction;
import com.example.EBook_Management_BE.entity.User;
import com.example.EBook_Management_BE.enums.Uri;
import com.example.EBook_Management_BE.mappers.TransactionMapper;
import com.example.EBook_Management_BE.responses.TransactionResponse;
import com.example.EBook_Management_BE.services.transaction.ITransactionRedisService;
import com.example.EBook_Management_BE.services.transaction.ITransactionService;
import com.example.EBook_Management_BE.services.user.IUserRedisService;
import com.example.EBook_Management_BE.services.user.IUserService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.TRANSACTION)
@Validated
@RequiredArgsConstructor
public class TransactionController {
	private final ITransactionService transactionService;
	private final ITransactionRedisService transactionRedisService;
	private final IUserService userService;
	private final IUserRedisService userRedisService;
	
	private final LocalizationUtils localizationUtils;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> getTransactionById(@PathVariable Long id) throws Exception {
		Transaction existingTransaction = transactionRedisService.getTransactionById(id);
		if (existingTransaction == null) {
			existingTransaction = transactionService.getTransactionById(id);
			
			transactionRedisService.saveTransactionById(id, existingTransaction);
		}
		
		TransactionResponse transactionResponse = transactionMapper.mapToTransactionResponse(existingTransaction);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.TRANSACTION_GET_BY_ID_SUCCESSFULLY))
				.data(transactionResponse)
				.build());
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ResponseObject> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) throws Exception {
		User user = userRedisService.getUserById(transactionDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(transactionDTO.getUserId());
			
			userRedisService.saveUserById(user.getId(), user);
		}
		
		Transaction transaction = transactionMapper.mapToTransactionEntity(transactionDTO);
		transaction.setUser(user);
		
		Transaction newTransaction = transactionService.createTransaction(transaction);
		transactionRedisService.saveTransactionById(newTransaction.getId(), newTransaction);
		
		TransactionResponse transactionResponse = transactionMapper.mapToTransactionResponse(newTransaction);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.CREATED)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.TRANSACTION_CREATE_SUCCESSFULLY))
				.data(transactionResponse)
				.build());
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseObject> updateTransaction(@PathVariable Long id,
			@Valid @RequestBody TransactionDTO transactionDTO) throws Exception {
		User user = userRedisService.getUserById(transactionDTO.getUserId());
		if (user == null) {
			user = userService.getUserById(transactionDTO.getUserId());
			
			userRedisService.saveUserById(user.getId(), user);
		}
		
		Transaction transaction = transactionMapper.mapToTransactionEntity(transactionDTO);
		transaction.setUser(user);
		
		transactionService.updateTransaction(id, transaction);
		transactionRedisService.saveTransactionById(id, transaction);
		
		TransactionResponse transactionResponse = transactionMapper.mapToTransactionResponse(transaction);
		
		return ResponseEntity.ok(ResponseObject.builder()
				.status(HttpStatus.OK)
				.message(localizationUtils.getLocalizedMessage(MessageKeys.TRANSACTION_UPDATE_SUCCESSFULLY))
				.data(transactionResponse)
				.build());
	}

}
