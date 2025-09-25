package com.bank.management.dto.request;

import lombok.Data;

@Data

public class UpdateUsersDTO {
    private Long id;
    private String dni;
    private String name;
    private String email;
}
