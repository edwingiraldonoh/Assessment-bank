package com.bank.management.service.impl;

import com.bank.management.entity.Users;
import com.bank.management.service.UsersService;
import org.springframework.stereotype.Service;
import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.repository.UsersRepository;
import com.bank.management.mapper.UsersMapper;
import java.util.List;


@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper mapper;

    public UsersServiceImpl(UsersRepository usersRepository, UsersMapper mapper) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
    }

    @Override
    public UsersDTO save(CreateUsersDTO createUsersDTO) {
        Users p = mapper.toEntity(createUsersDTO);
        Users usersSaved = usersRepository.save(p);
        UsersDTO usersDTOSaved = mapper.toDTO(usersSaved);
        return usersDTOSaved;

    }

    @Override
    public List<UsersDTO> getAll() {
        return usersRepository.findAll()
                .stream()
                .map(users -> mapper.toDTO(users))
                .toList();
    }

    @Override
    public UsersDTO getById(Long id) {
        return mapper.toDTO(usersRepository.getById(id));
    }

}

