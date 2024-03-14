package dev.renvl.dto;

import dev.renvl.model.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateTransactionRequest {

    @NotNull(message = "Account ID must not be blank")
    private Integer accountId;
    @NotNull(message = "Amount must not be null")
    private BigDecimal amount;
    private Currency currency;
    @NotBlank(message = "Direction of transaction must not be blank")
    private String direction;
    @NotBlank(message = "Description must not be blank")
    private String description;
}
