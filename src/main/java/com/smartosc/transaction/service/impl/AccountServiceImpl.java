package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.repository.AccountRepository;
import com.smartosc.transaction.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> createAccounts(final List<Account> accounts) {
        final var currentTime = Instant.now();
        accounts.forEach(it -> {
            it.setBalance(0);
            it.setCreatedAt(currentTime);
            it.setModifiedAt(currentTime);
            it.setStatus(false);
        });
        return accountRepository.saveAll(accounts);
    }
}
