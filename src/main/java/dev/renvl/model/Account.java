package dev.renvl.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private Integer accountId;
    private String country;
    private String customerId;

    public Account(String country, String customerId) {
        this.country = country;
        this.customerId = customerId;
    }
}
