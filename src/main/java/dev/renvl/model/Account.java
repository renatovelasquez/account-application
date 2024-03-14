package dev.renvl.model;

import lombok.Data;

@Data
public class Account {
    private Integer accountId;
    private String country;
    private String customerId;

    public Account(String country, String customerId) {
        this.country = country;
        this.customerId = customerId;
    }
}
