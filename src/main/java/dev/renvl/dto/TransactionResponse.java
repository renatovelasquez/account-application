package dev.renvl.dto;

import dev.renvl.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactions;
}
