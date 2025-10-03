package com.bank.management.mapper;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDTO(Account account);

    // CreateAccountDTO -> Entity
    @Mapping(source = "customerId", target = "usersId")
    Account toEntity(CreateAccountDTO dto);

    // Actualización de entidad con UpdateAccountDTO
    void updateEntity(@MappingTarget Account account, UpdateAccountDTO dto);
}