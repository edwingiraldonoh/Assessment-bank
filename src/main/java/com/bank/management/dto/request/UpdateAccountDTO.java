package com.bank.management.dto.request;

import lombok.Data;

@Data

public class UpdateAccountDTO {
    private Long id;
    private String accountNumber;
    private String accountType;
    private Long usersId;
    private Long operationsId;
}
