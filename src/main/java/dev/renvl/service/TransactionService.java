package dev.renvl.service;

import dev.renvl.dto.CreateTransactionRequest;
import dev.renvl.dto.CreateTransactionResponse;
import dev.renvl.dto.TransactionResponse;

public interface TransactionService {
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);

    TransactionResponse getTransactionByAccountId(Integer accountId);
}
