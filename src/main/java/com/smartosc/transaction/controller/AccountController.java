package com.smartosc.transaction.controller;

import com.smartosc.transaction.model.Account;
import com.smartosc.transaction.service.AccountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Account>> createAccounts(@RequestBody List<Account> accounts) {
        return ResponseEntity.ok(accountService.createAccounts(accounts));
    }
}
