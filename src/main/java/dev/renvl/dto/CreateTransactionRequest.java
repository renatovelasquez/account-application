package dev.renvl.dto;

import dev.renvl.model.Currency;
import dev.renvl.model.DirectionTransaction;
import dev.renvl.utils.MatchesEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class CreateTransactionRequest {

    @NotNull(message = "Account ID must not be blank")
    private Integer accountId;
    @NotNull(message = "Amount must not be blank")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount {jakarta.validation.constraints.DecimalMin.message}")
    @Digits(integer = 8, fraction = 2, message = "Amount {jakarta.validation.constraints.Digits.message}")
    private BigDecimal amount;
    @NotBlank(message = "Currency must not be blank")
    @MatchesEnum(enumClass = Currency.class, message = "Currency does not match the allowed values")
    private String currency;
    @NotBlank(message = "Direction of transaction must not be blank")
    @MatchesEnum(enumClass = DirectionTransaction.class, message = "Direction does not match allowed values")
    private String direction;
    @NotBlank(message = "Description must not be blank")
    private String description;
}
