package dev.renvl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AccountBalance implements Serializable {
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
