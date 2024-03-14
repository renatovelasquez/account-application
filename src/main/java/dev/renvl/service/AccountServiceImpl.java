package dev.renvl.service;

import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.dto.CreateAccountResponse;
import dev.renvl.exception.RecordNotFoundException;
import dev.renvl.mapper.AccountBalanceMapper;
import dev.renvl.mapper.AccountMapper;
import dev.renvl.model.Account;
import dev.renvl.model.AccountBalance;
import dev.renvl.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;

    public AccountServiceImpl(AccountMapper accountMapper, AccountBalanceMapper accountBalanceMapper) {
        this.accountMapper = accountMapper;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    @Override
    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account(request.getCountry(), request.getCustomerId());
        accountMapper.insert(account);

        List<AccountBalance> accountBalances = new ArrayList<>();
        for (Currency currency : request.getCurrencies()) {
            AccountBalance accountBalance = new AccountBalance(currency, account.getAccountId());
            accountBalanceMapper.insert(accountBalance);
            accountBalances.add(accountBalance);
        }

        return new CreateAccountResponse(account.getAccountId(), account.getCustomerId(), accountBalances);
    }

    @Override
    public CreateAccountResponse getAccount(Integer accountId) {
        Account account = accountMapper.findById(accountId);
        if (account == null) {
            throw new RecordNotFoundException("Account not found with id: " + accountId);
        }
        List<AccountBalance> accountBalances = accountBalanceMapper.findByAccountId(accountId);
        return new CreateAccountResponse(account.getAccountId(), account.getCustomerId(), accountBalances);
    }
}
