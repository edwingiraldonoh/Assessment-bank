package com.bank.management.controller;

import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.service.AccountService;
import com.bank.management.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@Tag(name = "Operations", description = "Puntos finales de gestión de cuentas")
public class OperationsController {
    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService){
        this.operationsService = operationsService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todas las cuentas", description = "Devuelve una lista de todas las cuentas")
    public ResponseEntity<List<OperationsDTO>> getOperations(){
        return ResponseEntity.ok(operationsService.getAll());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva o", description = "Crea una nueva cuenta")
    public ResponseEntity<OperationsDTO> saveOperations(@RequestBody CreateOperationsDTO createOperationsDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(operationsService.save(createOperationsDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener operacion por ID", description = "Devuelve los detalles de la cuenta segun el ID")
    public ResponseEntity<OperationsDTO> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(operationsService.getById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Actualizar operacion", description = "Actualiza los detalles de una cuenta")
    public ResponseEntity<OperationsDTO> updateAccount(@RequestBody UpdateOperationsDTO updateOperationsDTO){
        return ResponseEntity.ok(operationsService.update(updateOperationsDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar operacion", description = "Elimina una cuenta según el ID")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        operationsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}