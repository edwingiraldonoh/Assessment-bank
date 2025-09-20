package com.bank.management.controller;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAccount(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody CreateAccountDTO createAccountDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(createAccountDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getById(id));
    }
}