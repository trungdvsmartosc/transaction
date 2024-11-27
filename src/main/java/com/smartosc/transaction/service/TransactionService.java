package com.smartosc.transaction.service;

import com.smartosc.transaction.model.Transaction;
import com.smartosc.transaction.model.TransactionRequest;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactionsByUserId(Long id);

    Transaction createTransaction(TransactionRequest transactionRequest);
}
