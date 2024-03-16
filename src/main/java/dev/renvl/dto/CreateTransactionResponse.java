package dev.renvl.dto;

import dev.renvl.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateTransactionResponse {
    private Integer accountId;
    private Integer transactionId;
    private BigDecimal amount;
    private Currency currency;
    private String direction;
    private String description;
    private BigDecimal balance;
}
