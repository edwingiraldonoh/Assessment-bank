package com.bank.management.mapper;
import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface UsersMapper {

    Users toEntity(CreateUsersDTO dto);
    UsersDTO toDTO(Users users);

}