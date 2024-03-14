package dev.renvl.service;

import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.dto.CreateAccountResponse;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest request);

    CreateAccountResponse getAccount(Integer accountId);
}
