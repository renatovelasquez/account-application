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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionMapper transactionMapper;
    private final AccountBalanceMapper accountBalanceMapper;
    private final AccountMapper accountMapper;

    public TransactionServiceImpl(TransactionMapper transactionMapper, AccountBalanceMapper accountBalanceMapper,
                                  AccountMapper accountMapper) {
        this.transactionMapper = transactionMapper;
        this.accountBalanceMapper = accountBalanceMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public TransactionResponse getTransactionByAccountId(Integer accountId) {
        Account account = accountMapper.findById(accountId);
        if (account == null) {
            throw new RecordNotFoundException("Account not found with id: " + accountId);
        }
        LOGGER.info("Retrieved: {}", account);

        List<Transaction> transactions = transactionMapper.findByAccountId(account.getAccountId());
        LOGGER.info("Retrieved: {}", Arrays.toString(transactions.toArray()));

        return new TransactionResponse(transactions);
    }

    @Override
    @Transactional
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        AccountBalance accountBalance = retrieveAccountBalance(request);

        BigDecimal currentBalance = validateAmount(request, accountBalance.getAvailableAmount());
        accountBalance.setAvailableAmount(currentBalance);

        accountBalanceMapper.updateAvailableAmount(accountBalance);
        LOGGER.info("Updated: {}", accountBalance);

        Transaction transaction = new Transaction(request.getAmount(), Currency.valueOf(request.getCurrency()),
                request.getDirection(), request.getDescription(), request.getAccountId());
        transactionMapper.insert(transaction);
        LOGGER.info("Created: {}", transaction);

        return new CreateTransactionResponse(transaction.getAccountId(), transaction.getTransactionId(),
                transaction.getAmount(), transaction.getCurrency(), transaction.getDirection(),
                transaction.getDescription(), currentBalance);
    }

    private AccountBalance retrieveAccountBalance(CreateTransactionRequest request) {
        Account account = accountMapper.findById(request.getAccountId());
        AccountBalance accountBalance = accountBalanceMapper.findByAccountIdAndCurrency(
                request.getAccountId(), request.getCurrency());
        if (account == null || accountBalance == null) {
            throw new RecordNotFoundException("Account not found with id: " + request.getAccountId());
        }
        LOGGER.info("Retrieved: {}", accountBalance);
        return accountBalance;
    }

    private BigDecimal validateAmount(CreateTransactionRequest request, BigDecimal currentBalance) {
        if (DirectionTransaction.IN.name().compareTo(request.getDirection()) == 0) {
            currentBalance = currentBalance.add(request.getAmount());
        } else if (currentBalance.compareTo(request.getAmount()) >= 0) {
            currentBalance = currentBalance.subtract(request.getAmount());
        } else {
            throw new InsufficientFundsException("Insufficient funds to perform the transaction");
        }
        LOGGER.info("Validation for New Balance amount= {}", currentBalance);
        return currentBalance;
    }
}
