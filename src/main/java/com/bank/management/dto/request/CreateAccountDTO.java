package com.bank.management.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateAccountDTO {
    private String accountNumber;
    private String accountType;

}