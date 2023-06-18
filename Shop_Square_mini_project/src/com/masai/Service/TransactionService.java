package com.masai.Service;

import java.util.List;

import com.masai.entity.Transaction;
import com.masai.exceptions.TransactionException;

public interface TransactionService {

	public List<Transaction> viewCustomerTransactions(String email, List<Transaction> transactions)throws TransactionException;
	
	public List<Transaction> viewAllTransactions(List<Transaction> transactions) throws TransactionException;
}