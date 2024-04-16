package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.TransactionDTO;
import com.example.EBook_Management_BE.entity.Transaction;
import com.example.EBook_Management_BE.responses.TransactionResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
	TransactionMapper iNSTANCE = Mappers.getMapper(TransactionMapper.class);
	
	Transaction mapToTransactionEntity(TransactionDTO transactionDTO);
		
	TransactionResponse mapToTransactionResponse(Transaction transaction);
}
