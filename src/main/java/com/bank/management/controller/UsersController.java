package com.bank.management.controller;

import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Puntos finales de gestión de usuarios")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios")
    public ResponseEntity<List<UsersDTO>> getUsers(){
        return ResponseEntity.ok(usersService.getAll());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario con los datos proporcionados")
    public ResponseEntity<UsersDTO> saveUsers(@RequestBody CreateUsersDTO createUsersDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(createUsersDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve los detalles del usuario correspondiente al ID proporcionado")
    public ResponseEntity<UsersDTO> getUsersById(@PathVariable Long id){
        return ResponseEntity.ok(usersService.getById(id));
    }
}
