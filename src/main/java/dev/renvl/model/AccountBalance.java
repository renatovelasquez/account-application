package dev.renvl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountBalance {
    @JsonIgnore
    private Integer accountBalanceId;
    private BigDecimal availableAmount;
    private Currency currency;
    @JsonIgnore
    private Integer accountId;

    public AccountBalance(Currency currency, Integer accountId) {
        this.availableAmount = BigDecimal.ZERO;
        this.currency = currency;
        this.accountId = accountId;
    }
}
