package com.bank.management.controller;

import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.service.OperationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationsController {
    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService){
        this.operationsService = operationsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OperationsDTO>> getOperations(){
        return ResponseEntity.ok(operationsService.getAll());
    }

    @PostMapping
    public ResponseEntity<OperationsDTO> saveOperations(@RequestBody CreateOperationsDTO createOperationsDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(operationsService.save(createOperationsDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationsDTO> getOperationsById(@PathVariable Long id){
        return ResponseEntity.ok(operationsService.getById(id));
    }
}

