package com.bank.management.mapper;
import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" )
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "operations", ignore = true)
    @Mapping(target = "users", ignore = true)
    Account toEntity(CreateAccountDTO dto);

    @Mapping(target = "operationsId",
            expression = "java(account.getOperations() == null ? null :  account.getOperations().getId())")

    @Mapping(target = "usersId",
            expression = "java(account.getUsers() == null ? null : account.getUsers().getId())")

    AccountDTO toDTO(Account account);

    void updateEntity(@MappingTarget Account account, UpdateAccountDTO dto);
}