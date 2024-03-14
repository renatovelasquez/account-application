package dev.renvl.controller;

import dev.renvl.dto.CreateTransactionRequest;
import dev.renvl.dto.CreateTransactionResponse;
import dev.renvl.dto.TransactionResponse;
import dev.renvl.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions/v1")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<CreateTransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(transactionService.getTransactionByAccountId(id));
    }
}
