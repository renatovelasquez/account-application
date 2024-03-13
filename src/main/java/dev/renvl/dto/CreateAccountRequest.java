package dev.renvl.dto;

import dev.renvl.model.Currency;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreateAccountRequest {

    @NotBlank(message = "Customer ID must not be blank")
    private String customerId;
    @NotBlank(message = "Country must not be blank")
    private String country;

    //    @ValueOfEnum(enumClass = Currency.class)
//    @NotBlank(message = "Currencies must not be blank")
    private List<Currency> currencies;

    public CreateAccountRequest(String customerId, String country, List<Currency> currencies) {
        this.customerId = customerId;
        this.country = country;
        this.currencies = currencies;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "customerId='" + customerId + '\'' +
                ", country='" + country + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
