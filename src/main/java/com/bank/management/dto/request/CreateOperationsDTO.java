package com.bank.management.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateOperationsDTO {
    private String name;
    private String type;
    private Long usersId;
    private Long accountId;
}