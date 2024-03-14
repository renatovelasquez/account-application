package dev.renvl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetAccountRequest {

    @NotNull(message = "Account ID must not be blank")
    private Integer accountId;

}
