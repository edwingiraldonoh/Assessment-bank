package com.bank.management.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateUsersDTO {
    private String dni;
    private String name;
    private String email;
    private String accountNumber;
}