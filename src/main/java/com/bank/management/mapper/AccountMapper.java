package com.bank.management.mapper;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    // No necesitamos ignorar "users" porque ya no existe en el DTO
    AccountDTO toDTO(Account account);

    Account toEntity(CreateAccountDTO dto);

    void updateEntity(@MappingTarget Account account, UpdateAccountDTO dto);
}