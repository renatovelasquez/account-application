package dev.renvl.dto;

import dev.renvl.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
public class CreateTransactionResponse implements Serializable {
    private Integer accountId;
    private Integer transactionId;
    private BigDecimal amount;
    private Currency currency;
    private String direction;
    private String description;
    private BigDecimal balance;
}
