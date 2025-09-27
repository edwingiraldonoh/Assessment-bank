package com.bank.management.controller;

import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.request.UpdateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Puntos finales de gestión de usuarios")
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
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario")
    public ResponseEntity<UsersDTO> saveUsers(@Valid @RequestBody CreateUsersDTO createUsersDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.save(createUsersDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve los detalles del usuario segun el ID")
    public ResponseEntity<UsersDTO> getUsersById(@PathVariable Long id){
        return ResponseEntity.ok(usersService.getById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los detalles de un usuario existente")
    public ResponseEntity<UsersDTO> updateUsers(@Valid @RequestBody UpdateUsersDTO updateUsersDTO){
        return ResponseEntity.ok(usersService.update(updateUsersDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario según el ID")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id){
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
