package com.bank.management.dto.request;

import lombok.Data;

@Data

public class UpdateAccountDTO {
    private Long id;
    private String AccountNumber;
    private String accountType;
}
