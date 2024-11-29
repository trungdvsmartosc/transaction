package com.smartosc.transaction.service.impl;

import com.smartosc.transaction.base.TestBase;
import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Import(TestBase.class)
public class AccountServiceIT {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccounts() {
        Account account = new Account();
        account.setUsername("trungdv");
        account.setPassword("trungpw");
        account.setBalance(111);
        account.setStatus(true);
        account.setEmail("trungdv@gmail.com");
        account.setNumber("0194814215");

        when(accountRepository.saveAll(any(List.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Account> accounts = accountService.createAccounts(List.of(account));

        assertThat(accounts).hasSize(1);

        Account accountSaved = accounts.getFirst();

        assertEquals(account.getUsername(), accountSaved.getUsername());
        assertEquals(account.getPassword(), accountSaved.getPassword());
        assertEquals(account.getEmail(), accountSaved.getEmail());
        assertEquals(account.getNumber(), accountSaved.getNumber());

        assertEquals(0, accountSaved.getBalance());
        assertFalse(accountSaved.isStatus());
        assertNotNull(accountSaved.getCreatedAt());
        assertNotNull(accountSaved.getModifiedAt());
    }
}
