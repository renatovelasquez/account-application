package dev.renvl.dto;

import dev.renvl.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactions;
}
