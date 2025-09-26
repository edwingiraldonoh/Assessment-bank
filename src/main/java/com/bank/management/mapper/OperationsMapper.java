package com.bank.management.mapper;
import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" )
public interface OperationsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "users", ignore = true)
    Operations toEntity(CreateOperationsDTO dto);
    Account map(Long accountId);

    @Mapping(target = "account",
            expression = "java(operations.getAccount() == null ? null :  operations.getAccount().getId())")

    @Mapping(target = "usersId",
            expression = "java(operations.getUsers() == null ? null : operations.getUsers().getId())")
    OperationsDTO toDTO(Operations operations);
    void updateEntity(@MappingTarget Operations operations, UpdateOperationsDTO dto);
}