package com.bank.management.service;

import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import java.util.List;

public interface UsersService {
    //save, update, delete, getById, getAll
    UsersDTO save(CreateUsersDTO createUsersDTO);
    List<UsersDTO> getAll();
    UsersDTO getById(Long id);

}
