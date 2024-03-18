package dev.renvl.controller;

import dev.renvl.dto.AccountResponse;
import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.model.AccountBalance;
import dev.renvl.model.Currency;
import dev.renvl.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void AccountController_GetAccount_ReturnsAccountResponse() {
        Integer accountId = 1;

        AccountBalance accountBalanceEUR = new AccountBalance(Currency.EUR, accountId);
        AccountBalance accountBalanceUSD = new AccountBalance(Currency.USD, accountId);
        List<AccountBalance> accountBalanceList = Arrays.asList(accountBalanceEUR, accountBalanceUSD);

        AccountResponse expectedResponse = new AccountResponse(accountId, "00001", accountBalanceList);
        when(accountService.getAccount(accountId)).thenReturn(expectedResponse);

        ResponseEntity<AccountResponse> accountCreated = accountController.getAccount(accountId);
        assertEquals(HttpStatus.OK, accountCreated.getStatusCode());
        assertEquals(expectedResponse, accountCreated.getBody());
    }

    @Test
    void AccountController_CreateAccount_ReturnsAccountResponse() {
        CreateAccountRequest request = CreateAccountRequest.builder().customerId("00001").country("Country_1")
                .currencies(Arrays.asList(Currency.EUR.name(), Currency.GBP.name())).build();
        Integer accountId = 1;
        AccountBalance accountBalanceEUR = new AccountBalance(Currency.EUR, accountId);
        AccountBalance accountBalanceUSD = new AccountBalance(Currency.GBP, accountId);
        List<AccountBalance> accountBalanceList = Arrays.asList(accountBalanceEUR, accountBalanceUSD);

        AccountResponse expectedResponse = new AccountResponse(accountId, request.getCustomerId(), accountBalanceList);
        when(accountService.createAccount(request)).thenReturn(expectedResponse);

        ResponseEntity<AccountResponse> accountCreated = accountController.createAccount(request);
        assertEquals(HttpStatus.CREATED, accountCreated.getStatusCode());
        assertEquals(expectedResponse, accountCreated.getBody());
    }
}
