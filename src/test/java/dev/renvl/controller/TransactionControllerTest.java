package dev.renvl.controller;

import dev.renvl.dto.CreateTransactionRequest;
import dev.renvl.dto.CreateTransactionResponse;
import dev.renvl.dto.TransactionResponse;
import dev.renvl.model.Currency;
import dev.renvl.model.DirectionTransaction;
import dev.renvl.model.Transaction;
import dev.renvl.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void TransactionController_GetTransaction_ReturnsTransactionResponse() {
        Integer accountId = 1;

        Transaction transactionIN = new Transaction(BigDecimal.ONE, Currency.USD, DirectionTransaction.IN.name(), "Description about transaction IN", accountId);
        Transaction transactionOUT = new Transaction(BigDecimal.ONE, Currency.EUR, DirectionTransaction.OUT.name(), "Description about transaction OUT", accountId);
        List<Transaction> transactions = Arrays.asList(transactionIN, transactionOUT);
        TransactionResponse expectedResponse = new TransactionResponse(transactions);

        when(transactionService.getTransactionByAccountId(accountId)).thenReturn(expectedResponse);

        ResponseEntity<TransactionResponse> accountCreated = transactionController.getTransaction(accountId);
        assertEquals(HttpStatus.OK, accountCreated.getStatusCode());
        assertEquals(expectedResponse, accountCreated.getBody());
    }

    @Test
    void TransactionController_CreateTransaction_ReturnsTransactionResponse() {
        CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(0).amount(BigDecimal.ONE)
                .currency(Currency.EUR.name()).direction(DirectionTransaction.IN.name()).description("Description about transaction IN").build();

        Integer transactionId = 1;
        CreateTransactionResponse expectedResponse = new CreateTransactionResponse(request.getAccountId(), transactionId,
                request.getAmount(), Currency.valueOf(request.getCurrency()), request.getDirection(), request.getDescription(), BigDecimal.TEN);

        when(transactionService.createTransaction(request)).thenReturn(expectedResponse);

        ResponseEntity<CreateTransactionResponse> transactionCreated = transactionController.createTransaction(request);
        assertEquals(HttpStatus.OK, transactionCreated.getStatusCode());
        assertEquals(expectedResponse, transactionCreated.getBody());
    }

}