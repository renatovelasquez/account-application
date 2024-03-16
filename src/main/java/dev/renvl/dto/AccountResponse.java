package dev.renvl.dto;

import dev.renvl.model.AccountBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AccountResponse {
    private Integer accountId;
    private String customerId;
    private List<AccountBalance> balances;
}
