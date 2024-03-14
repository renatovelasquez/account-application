package dev.renvl.dto;

import dev.renvl.model.AccountBalance;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateAccountResponse {

    private Integer accountId;
    private String customerId;
    private List<AccountBalance> balances;

}
