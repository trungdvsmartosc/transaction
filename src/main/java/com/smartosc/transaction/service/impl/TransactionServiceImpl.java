package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.model.Transaction;
import com.smartosc.transaction.model.TransactionRequest;
import com.smartosc.transaction.repository.AccountRepository;
import com.smartosc.transaction.repository.TransactionRepository;
import com.smartosc.transaction.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Transaction> getTransactionsByUserId(final Long id) {
        return transactionRepository.findTransactionsBySenderAccountOrReceiverAccountId(id);
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        final Long senderAccountId = transactionRequest.getSenderId();
        final Long receiverAccountId = transactionRequest.getReceiverId();
        validateTransaction(senderAccountId, receiverAccountId);
        return transactionProcessor(transactionRequest, senderAccountId, receiverAccountId);
    }

    @Transactional
    Transaction transactionProcessor(TransactionRequest transactionRequest, Long senderAccountId, Long receiverAccountId) {
        Account senderAccountSaved = withdraw(senderAccountId, transactionRequest.getAmount());
        Account receiverAccountSaved = deposit(receiverAccountId, transactionRequest.getAmount());
        Transaction transaction = buildTransaction(transactionRequest, senderAccountSaved, receiverAccountSaved);
        return transactionRepository.save(transaction);
    }

    void validateTransaction(Long senderAccountId, Long receiverAccountId) {
        if (senderAccountId == null || receiverAccountId == null) {
            log.debug("Invalid transaction");
            throw new IllegalArgumentException("Invalid transaction");
        }
        if (Objects.equals(senderAccountId, receiverAccountId)) {
            log.debug("Account with id {} must not be the same", senderAccountId);
            throw new IllegalArgumentException("Account with id " + senderAccountId + " must not be the same");
        }
    }

    Transaction buildTransaction(TransactionRequest transactionRequest, Account senderAccount, Account receiverAccount) {
        final var currentTime = Instant.now();
        Transaction transaction = new Transaction();
        transaction.setSender(senderAccount);
        transaction.setReceiver(receiverAccount);
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setStatus(Transaction.STATUS.PROCESSING);
        transaction.setTransactionDate(currentTime);
        return transaction;
    }

    public Account withdraw(Long id, double amount) {
        final Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " does not exist"));
        final double newBalance = account.getBalance() - amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    public Account deposit(Long id, double amount) {
        final Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " does not exist"));
        final double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }
}
