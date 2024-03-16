package dev.renvl.service;

import dev.renvl.dto.AccountResponse;
import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.exception.RecordNotFoundException;
import dev.renvl.mapper.AccountBalanceMapper;
import dev.renvl.mapper.AccountMapper;
import dev.renvl.model.Account;
import dev.renvl.model.AccountBalance;
import dev.renvl.model.Currency;
import dev.renvl.publisher.RabbitMQProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.UnfinishedStubbingException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountMapper accountMapper;
    @Mock
    private AccountBalanceMapper accountBalanceMapper;
    @Mock
    private RabbitMQProducer producer;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void AccountService_CreateAccount_ReturnsAccountResponse() {
        CreateAccountRequest request = CreateAccountRequest.builder().customerId("00001").country("Country_1")
                .currencies(Arrays.asList(Currency.EUR.name(), Currency.GBP.name())).build();

        Account account = new Account(request.getCountry(), request.getCustomerId());
        accountMapper.insert(account);
        verify(accountMapper, times(1)).insert(account);

        for (Currency currency : request.getCurrencies()) {
            AccountBalance accountBalance = new AccountBalance(currency, account.getAccountId());
            accountBalanceMapper.insert(accountBalance);
        }
        verify(accountBalanceMapper, times(request.getCurrencies().size())).insert(any(AccountBalance.class));

        producer.sendMessage(AccountResponse.class);
        verify(producer, times(1)).sendMessage(AccountResponse.class);

        AccountResponse accountCreated = accountService.createAccount(request);
        assertNotNull(accountCreated);
    }

    @Test
    void givenNullCountry_whenCreateAccount_thenThrowsException() {
        Account account = new Account(null, "00001");
        assertThrows(UnfinishedStubbingException.class, () -> doThrow().when(accountMapper).insert(account));
        accountMapper.insert(account);
    }

    @Test
    void givenNullCustomer_whenCreateAccount_thenThrowsException() {
        Account account = new Account("Country_1", null);
        assertThrows(UnfinishedStubbingException.class, () -> doThrow().when(accountMapper).insert(account));
        accountMapper.insert(account);
    }

    @Test
    void AccountService_GetAccount_ReturnsAccountResponse() {
        Integer accountId = 1;
        Account account = new Account("Country_1", "00001");
        when(accountMapper.findById(accountId)).thenReturn(account);

        AccountBalance accountBalanceEUR = new AccountBalance(Currency.EUR, account.getAccountId());
        AccountBalance accountBalanceUSD = new AccountBalance(Currency.USD, account.getAccountId());
        List<AccountBalance> accountBalanceList = Arrays.asList(accountBalanceEUR, accountBalanceUSD);
        when(accountBalanceMapper.findByAccountId(accountId)).thenReturn(accountBalanceList);

        AccountResponse accountCreated = accountService.getAccount(accountId);
        assertNotNull(accountCreated);
    }

    @Test
    void GivenNotFoundAccountId_whenGetAccount_thenThrowsException() {
        Integer accountId = 0;
        when(accountMapper.findById(accountId)).thenReturn(null);

        assertThrows(RecordNotFoundException.class, () -> accountService.getAccount(accountId));
    }

    @Test
    void GivenNullAccountId_whenGetAccount_thenThrowsException() {
        assertThrows(UnfinishedStubbingException.class, () -> doThrow().when(accountMapper).findById(null));
        accountMapper.findById(null);
    }

}