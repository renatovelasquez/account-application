package dev.renvl.model;

import java.util.Objects;

public class Account {
    private Integer accountId;
    private String country;
    private String customerId;

    public Account(String country, String customerId) {
        this.country = country;
        this.customerId = customerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", country='" + country + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
