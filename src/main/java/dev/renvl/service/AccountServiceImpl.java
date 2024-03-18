package dev.renvl.service;

import dev.renvl.dto.AccountResponse;
import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.exception.RecordNotFoundException;
import dev.renvl.mapper.AccountBalanceMapper;
import dev.renvl.mapper.AccountMapper;
import dev.renvl.mapper.CustomerMapper;
import dev.renvl.model.Account;
import dev.renvl.model.AccountBalance;
import dev.renvl.model.Currency;
import dev.renvl.model.Customer;
import dev.renvl.publisher.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;
    private final RabbitMQProducer producer;

    public AccountServiceImpl(CustomerMapper customerMapper, AccountMapper accountMapper, AccountBalanceMapper accountBalanceMapper, RabbitMQProducer producer) {
        this.customerMapper = customerMapper;
        this.accountMapper = accountMapper;
        this.accountBalanceMapper = accountBalanceMapper;
        this.producer = producer;
    }

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        Customer customer = customerMapper.getCustomer(request.getCustomerId());
        if (customer == null) {
            throw new RecordNotFoundException("Customer not found with id: " + request.getCustomerId());
        }

        Account account = new Account(request.getCountry(), customer.getCustomerId());
        accountMapper.insert(account);

        List<AccountBalance> accountBalances = new ArrayList<>();
        for (Currency currency : request.getCurrencies()) {
            AccountBalance accountBalance = new AccountBalance(currency, account.getAccountId());
            accountBalanceMapper.insert(accountBalance);
            accountBalances.add(accountBalance);
        }

        AccountResponse accountResponse = new AccountResponse(account.getAccountId(), account.getCustomerId(), accountBalances);
        producer.sendMessage(accountResponse);
        return accountResponse;
    }

    @Override
    public AccountResponse getAccount(Integer accountId) {
        Account account = accountMapper.findById(accountId);
        if (account == null) {
            throw new RecordNotFoundException("Account not found with id: " + accountId);
        }
        LOGGER.info("Retrieved: {}", account);

        List<AccountBalance> accountBalances = accountBalanceMapper.findByAccountId(accountId);
        LOGGER.info("Retrieved: {}", Arrays.toString(accountBalances.toArray()));

        return new AccountResponse(account.getAccountId(), account.getCustomerId(), accountBalances);
    }
}
