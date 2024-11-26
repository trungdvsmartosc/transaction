package com.smartosc.transaction.controller;

import com.smartosc.transaction.model.Transaction;
import com.smartosc.transaction.repository.AccountRepository;
import com.smartosc.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(id));
    }

    @PostMapping
    public ResponseEntity<List<Transaction>> createTransactions(@RequestBody List<Transaction> transactions) {
        return ResponseEntity.ok(transactionService.createTransactions(transactions));
    }
}
