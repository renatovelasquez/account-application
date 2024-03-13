package dev.renvl.dto;

import dev.renvl.model.AccountBalance;

import java.util.List;

public class CreateAccountResponse {

    private Integer accountId;
    private String customerId;
    private List<AccountBalance> balances;

    public CreateAccountResponse(Integer accountId, String customerId, List<AccountBalance> currencies) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.balances = currencies;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<AccountBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<AccountBalance> balances) {
        this.balances = balances;
    }

    @Override
    public String toString() {
        return "CreateAccountResponse{" +
                "accountId=" + accountId +
                ", customerId='" + customerId + '\'' +
                ", currencies=" + balances +
                '}';
    }
}
