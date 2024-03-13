package dev.renvl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Objects;

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

    public int getAccountBalanceId() {
        return accountBalanceId;
    }

    public void setAccountBalanceId(int accountBalanceId) {
        this.accountBalanceId = accountBalanceId;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(accountBalanceId, that.accountBalanceId) && Objects.equals(availableAmount, that.availableAmount) && currency == that.currency && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountBalanceId, availableAmount, currency, accountId);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountBalanceId=" + accountBalanceId +
                ", availableAmount=" + availableAmount +
                ", currency=" + currency +
                ", accountId=" + accountId +
                '}';
    }
}
