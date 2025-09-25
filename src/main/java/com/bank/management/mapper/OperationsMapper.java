package com.bank.management.mapper;

import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.entity.Operations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OperationsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    Operations toEntity(CreateOperationsDTO dto);
    OperationsDTO toDTO(Operations operations);
    void updateEntity(@MappingTarget Operations operations, UpdateOperationsDTO dto);
}
