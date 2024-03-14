package dev.renvl.service;

import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.dto.AccountResponse;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccount(Integer accountId);
}
