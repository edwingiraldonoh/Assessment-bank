package com.bank.management.controller;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Tag(name = "Cuentas", description = "Puntos finales de gestión de cuentas")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todas las cuentas", description = "Devuelve una lista de todas las cuentas")
    public ResponseEntity<List<AccountDTO>> getAccount(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva cuenta", description = "Crea una nueva cuenta")
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody CreateAccountDTO createAccountDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.save(createAccountDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta por ID", description = "Devuelve los detalles de la cuenta segun el ID")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getById(id));
    }
}