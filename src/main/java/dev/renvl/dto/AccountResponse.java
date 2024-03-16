package dev.renvl.dto;

import dev.renvl.model.AccountBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccountResponse implements Serializable {
    private Integer accountId;
    private String customerId;
    private List<AccountBalance> balances;
}
