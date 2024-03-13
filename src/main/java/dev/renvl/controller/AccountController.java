package dev.renvl.controller;

import dev.renvl.dto.CreateAccountRequest;
import dev.renvl.dto.CreateAccountResponse;
import dev.renvl.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/v1")
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAccountResponse> process(@RequestBody @Valid CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}
