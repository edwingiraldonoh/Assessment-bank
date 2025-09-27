package com.bank.management.service.impl;

import com.bank.management.dto.request.UpdateUsersDTO;
import com.bank.management.entity.Users;
import com.bank.management.exceptions.DataNotFountException;
import com.bank.management.exceptions.DuplicatedDataException;
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
        if(usersRepository.existsByDni(createUsersDTO.getDni())){
            throw new DuplicatedDataException("users", createUsersDTO.getDni());
        }
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
        /*if (usersRepository.existsById(id)){
            return mapper.toDTO(usersRepository.findById(id).get());
        }else{
            throw new UsersNotFountException(id);
        }*/

        return mapper.toDTO(usersRepository.findById(id).orElseThrow(() -> new DataNotFountException(id, "Users")));

    }

    @Override
    public UsersDTO update(UpdateUsersDTO updateUsersDTO) {
        Users users = usersRepository.findById(updateUsersDTO.getId()).get();
        mapper.updateEntity(users, updateUsersDTO);
        return mapper.toDTO(usersRepository.save(users));
    }

    @Override
    public void delete(Long id) {
        if (usersRepository.findById(id).isEmpty()) {
            System.out.println("El usuario con el id " + id + " no se encuentra o no existe.");
        }
        usersRepository.deleteById(id);
    }

}

