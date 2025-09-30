package com.bank.management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data

public class UpdateUsersDTO {
    private Long id;

    @NotBlank(message = "DNI no puede estar vacio")
    @Pattern(regexp = "^[0-9]{10}$", message = "DNI debe tener 10 numeros")
    @Positive(message = "DNI debe ser un numero positivo")
    private String dni;

    @NotBlank(message =  "El tipo de cuenta no puede estar vacio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]{2,20}$", message = "El tipo de cuents debe tener solo letras y espacios")
    private String name;

    @Email
    private String email;
    private Long account;
}
