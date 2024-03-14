package dev.renvl.service;

import dev.renvl.dto.CreateTransactionRequest;
import dev.renvl.dto.CreateTransactionResponse;
import dev.renvl.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse getTransactionByAccountId(Integer accountId);

    CreateTransactionResponse createTransaction(CreateTransactionRequest request);
}
