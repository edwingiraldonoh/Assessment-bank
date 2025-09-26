package com.bank.management.mapper;
import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.request.UpdateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring" )
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "operations", ignore = true)
    @Mapping(target = "account", ignore = true)
    Users toEntity(CreateUsersDTO dto);

    // CORRECCIÓN PREVIA: Se cambió 'account.accountId' a 'account.id'
    @Mapping(source = "account.id", target = "account")
    UsersDTO toDTO(Users users);

    // CORRECCIÓN NECESARIA: Esto resuelve el error "Can't map property Long account to Account account"
    @Mapping(target = "account", ignore = true)
    void updateEntity(@MappingTarget Users users, UpdateUsersDTO dto);
}