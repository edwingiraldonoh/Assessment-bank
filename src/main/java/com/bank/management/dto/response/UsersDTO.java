package com.bank.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UsersDTO {
    private Long id;
    private String dni;
    private String name;
    private String email;
}