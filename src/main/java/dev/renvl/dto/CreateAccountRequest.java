package dev.renvl.dto;

import dev.renvl.model.Currency;
import dev.renvl.utils.ListMatchesEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message = "Customer ID must not be blank")
    private String customerId;
    @NotBlank(message = "Country must not be blank")
    private String country;
    @ListMatchesEnum(enumClass = Currency.class, message = "List elements do not match the Currency values")
    private List<String> currencies;

    public List<Currency> getCurrencies() {
        return currencies.stream()
                .map(Currency::valueOf)
                .collect(Collectors.toList());
    }
}
