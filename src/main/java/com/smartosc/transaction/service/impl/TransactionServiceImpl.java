package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.model.Transaction;
import com.smartosc.transaction.repository.AccountRepository;
import com.smartosc.transaction.repository.TransactionRepository;
import com.smartosc.transaction.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
    public List<Transaction> createTransactions(final List<Transaction> transactions) {
        final var currentTime = Instant.now();
        final List<Transaction> validTransactions = transactions.stream().filter(it -> {
            final Long senderAccountId = it.getSenderAccount().getId();
            final var senderAccount = accountRepository.findById(senderAccountId);
            if (senderAccount.isEmpty()) {
                log.debug("Account with id {} not found", senderAccountId);
                return false;
            }
            final Long receiverAccountId = it.getReceiverAccount().getId();
            final var receiverAccount = accountRepository.findById(receiverAccountId);
            if (receiverAccount.isEmpty()) {
                log.debug("Account with id {} not found", receiverAccountId);
                return false;
            }

            if (Objects.equals(senderAccountId, receiverAccountId)) {
                log.debug("Account with id {} must not be the same", senderAccountId);
                return false;
            }

            it.setSenderAccount(senderAccount.get());
            it.setReceiverAccount(receiverAccount.get());
            it.setStatus(Transaction.STATUS.PROCESSING);
            it.setTransactionDate(currentTime);
            return true;
        }).toList();
        return transactionRepository.saveAll(validTransactions);
    }
}
