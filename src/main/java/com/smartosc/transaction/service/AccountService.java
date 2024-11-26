package com.smartosc.transaction.service;

import com.smartosc.transaction.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> createAccounts(List<Account> accounts);
}
