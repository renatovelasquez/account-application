package dev.renvl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Transaction {
    private Integer transactionId;
    private BigDecimal amount;
    private Currency currency;
    private String direction;
    private String description;
    private Integer accountId;

    public Transaction(BigDecimal amount, Currency currency, String direction, String description, Integer accountId) {
        this.amount = amount;
        this.currency = currency;
        this.direction = direction;
        this.description = description;
        this.accountId = accountId;
    }
}
