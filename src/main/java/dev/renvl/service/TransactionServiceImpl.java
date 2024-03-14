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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

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
        return new TransactionResponse(transactionMapper.findByAccountId(account.getAccountId()));
    }

    @Override
    @Transactional
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        AccountBalance accountBalance = validateAccountBalance(request);

        BigDecimal currentBalance = validateAmount(request, accountBalance.getAvailableAmount());
        accountBalance.setAvailableAmount(currentBalance);

        accountBalanceMapper.updateAvailableAmount(accountBalance);

        Transaction transaction = new Transaction(request.getAmount(), Currency.valueOf(request.getCurrency()),
                request.getDirection(), request.getDescription(), request.getAccountId());
        transactionMapper.insert(transaction);
        return new CreateTransactionResponse(transaction.getAccountId(), transaction.getTransactionId(),
                transaction.getAmount(), transaction.getCurrency(), transaction.getDirection(),
                transaction.getDescription(), currentBalance);
    }

    private AccountBalance validateAccountBalance(CreateTransactionRequest request) {
        Account account = accountMapper.findById(request.getAccountId());
        AccountBalance accountBalance = accountBalanceMapper.findByAccountIdAndCurrency(
                request.getAccountId(), request.getCurrency());
        if (account == null || accountBalance == null) {
            throw new RecordNotFoundException("Account not found with id: " + request.getAccountId());
        }
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
        return currentBalance;
    }
}
