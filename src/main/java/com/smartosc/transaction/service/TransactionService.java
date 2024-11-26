package com.smartosc.transaction.service;

import com.smartosc.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactionsByUserId(Long id);

    List<Transaction> createTransactions(List<Transaction> transactions);
}
