package com.bank.management.controller;

import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.service.OperationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@Tag(name = "Operaciones", description = "Puntos finales de gestión de operaciones")
public class OperationsController {
    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService){
        this.operationsService = operationsService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todas las operaciones", description = "Devuelve una lista de todas las operaciones")
    public ResponseEntity<List<OperationsDTO>> getOperations(){
        return ResponseEntity.ok(operationsService.getAll());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva operación", description = "Crea una nueva operación")
    public ResponseEntity<OperationsDTO> saveOperations(@RequestBody CreateOperationsDTO createOperationsDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(operationsService.save(createOperationsDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener operación por ID", description = "Devuelve los detalles de la operación seghun el ID")
    public ResponseEntity<OperationsDTO> getOperationsById(@PathVariable Long id){
        return ResponseEntity.ok(operationsService.getById(id));
    }
}

