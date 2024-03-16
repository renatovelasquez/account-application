package dev.renvl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
