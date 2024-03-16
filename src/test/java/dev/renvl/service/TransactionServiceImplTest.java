package dev.renvl.service;

import dev.renvl.dto.CreateTransactionRequest;
import dev.renvl.dto.CreateTransactionResponse;
import dev.renvl.dto.TransactionResponse;
import dev.renvl.exception.InsufficientFundsException;
import dev.renvl.exception.RecordNotFoundException;
import dev.renvl.mapper.AccountBalanceMapper;
import dev.renvl.mapper.AccountMapper;
import dev.renvl.mapper.TransactionMapper;
import dev.renvl.model.*;
import dev.renvl.publisher.RabbitMQProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.UnfinishedStubbingException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private AccountBalanceMapper accountBalanceMapper;
    @Mock
    private RabbitMQProducer producer;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void TransactionService_CreateTransactionIN_ReturnsCreateTransactionResponse() {
        CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(1).amount(BigDecimal.ONE)
                .currency(Currency.EUR.name()).direction(DirectionTransaction.IN.name()).description("Description about transaction IN").build();

        Account account = new Account("Country_1", "00001");
        account.setAccountId(request.getAccountId());
        when(accountMapper.findById(request.getAccountId())).thenReturn(account);

        AccountBalance accountBalanceEUR = new AccountBalance(Currency.EUR, account.getAccountId());
        when(accountBalanceMapper.findByAccountIdAndCurrency(request.getAccountId(), request.getCurrency())).thenReturn(accountBalanceEUR);

        producer.updateMessage(AccountBalance.class);
        verify(producer, times(1)).updateMessage(AccountBalance.class);

        Transaction transaction = new Transaction(request.getAmount(), Currency.valueOf(request.getCurrency()), request.getDirection(), request.getDescription(), request.getAccountId());
        transactionMapper.insert(transaction);
        verify(transactionMapper, times(1)).insert(transaction);

        producer.sendMessage(CreateTransactionResponse.class);
        verify(producer, times(1)).sendMessage(CreateTransactionResponse.class);

        CreateTransactionResponse response = transactionService.createTransaction(request);
        assertNotNull(response);
    }

    @Test
    void TransactionService_CreateTransactionOUT_ReturnsCreateTransactionResponse() {
        CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(1).amount(BigDecimal.ONE)
                .currency(Currency.EUR.name()).direction(DirectionTransaction.OUT.name()).description("Description about transaction IN").build();

        Account account = new Account("Country_1", "00001");
        account.setAccountId(request.getAccountId());
        when(accountMapper.findById(request.getAccountId())).thenReturn(account);

        AccountBalance accountBalanceEUR = new AccountBalance(Currency.EUR, account.getAccountId());
        accountBalanceEUR.setAvailableAmount(BigDecimal.ONE);
        when(accountBalanceMapper.findByAccountIdAndCurrency(request.getAccountId(), request.getCurrency())).thenReturn(accountBalanceEUR);

        producer.updateMessage(AccountBalance.class);
        verify(producer, times(1)).updateMessage(AccountBalance.class);

        Transaction transaction = new Transaction(request.getAmount(), Currency.valueOf(request.getCurrency()), request.getDirection(), request.getDescription(), request.getAccountId());
        transactionMapper.insert(transaction);
        verify(transactionMapper, times(1)).insert(transaction);

        producer.sendMessage(CreateTransactionResponse.class);
        verify(producer, times(1)).sendMessage(CreateTransactionResponse.class);

        CreateTransactionResponse response = transactionService.createTransaction(request);
        assertNotNull(response);
    }

    @Test
    void GivenNotFoundAccountId_whenCreateTransaction_thenThrowsException() {
        CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(0).amount(BigDecimal.ONE)
                .currency(Currency.EUR.name()).direction(DirectionTransaction.IN.name()).description("Description about transaction IN").build();
        when(accountMapper.findById(request.getAccountId())).thenReturn(null);
        when(accountBalanceMapper.findByAccountIdAndCurrency(request.getAccountId(), request.getCurrency())).thenReturn(null);

        assertThrows(RecordNotFoundException.class, () -> transactionService.createTransaction(request));
    }

    @Test
    void GivenAmountGreaterThanCurrentBalanceDirectionOUT_whenGetTransactionByAccountId_thenThrowsException() {
        CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(1).amount(BigDecimal.ONE)
                .currency(Currency.EUR.name()).direction(DirectionTransaction.OUT.name()).description("Description about transaction IN").build();

        Account account = new Account("Country_1", "00001");
        account.setAccountId(request.getAccountId());
        when(accountMapper.findById(request.getAccountId())).thenReturn(account);

        AccountBalance accountBalanceEUR = new AccountBalance(Currency.EUR, account.getAccountId());
        accountBalanceEUR.setAvailableAmount(BigDecimal.ZERO);
        when(accountBalanceMapper.findByAccountIdAndCurrency(request.getAccountId(), request.getCurrency())).thenReturn(accountBalanceEUR);

        assertThrows(InsufficientFundsException.class, () -> transactionService.createTransaction(request));
    }

    @Test
    void TransactionService_GetTransactionByAccountId_ReturnsTransactionResponse() {
        Integer accountId = 1;
        Account account = new Account("Country_1", "00001");
        account.setAccountId(accountId);
        when(accountMapper.findById(accountId)).thenReturn(account);

        Transaction transactionIN = new Transaction(BigDecimal.ONE, Currency.USD, DirectionTransaction.IN.name(), "Description about transaction IN", account.getAccountId());
        Transaction transactionOUT = new Transaction(BigDecimal.ONE, Currency.EUR, DirectionTransaction.OUT.name(), "Description about transaction OUT", account.getAccountId());
        List<Transaction> transactions = Arrays.asList(transactionIN, transactionOUT);
        when(transactionMapper.findByAccountId(accountId)).thenReturn(transactions);

        TransactionResponse response = transactionService.getTransactionByAccountId(account.getAccountId());
        assertNotNull(response);
    }

    @Test
    void GivenNotFoundAccountId_whenGetTransactionByAccountId_thenThrowsException() {
        Integer accountId = 0;
        when(accountMapper.findById(accountId)).thenReturn(null);

        assertThrows(RecordNotFoundException.class, () -> transactionService.getTransactionByAccountId(accountId));
    }

    @Test
    void GivenNullAccountId_whenGetTransactionByAccountId_thenThrowsException() {
        assertThrows(UnfinishedStubbingException.class, () -> doThrow().when(accountMapper).findById(null));
        accountMapper.findById(null);
    }
}