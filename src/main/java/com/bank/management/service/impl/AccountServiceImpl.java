package com.bank.management.service.impl;

import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import com.bank.management.entity.Users;
import com.bank.management.repository.OperationsRepository;
import com.bank.management.repository.UsersRepository;
import com.bank.management.service.AccountService;
import org.springframework.stereotype.Service;
import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.repository.AccountRepository;
import com.bank.management.mapper.AccountMapper;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    private final OperationsRepository operationsRepository;
    private final UsersRepository usersRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper mapper,
                              OperationsRepository operationsRepository,
                              UsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.operationsRepository = operationsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public AccountDTO save(CreateAccountDTO dto) {
        // <operationsId, usersId, accountNumber, accountType>
        Operations operations = operationsRepository.findById(dto.getOperationsId()).get();
        Users users = usersRepository.findById(dto.getUsersId()).get();

        // Asignar una operacion y un usuario a la cuenta
        users.setOperations(operations);
        usersRepository.save(users);

        // Crear la cuenta y asignar las relaciones
        Account appToSave = mapper.toEntity(dto);
        appToSave.setOperations(operations);
        appToSave.setUsers(users);

        return mapper.toDTO(accountRepository.save(appToSave));
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(account -> mapper.toDTO(account))
                .toList();
    }

    @Override
    public AccountDTO getById(Long id) {
        return mapper.toDTO(accountRepository.getById(id));
    }

    @Override
    public AccountDTO update(UpdateAccountDTO updateAccountDTO) {
        Account account = accountRepository.findById(updateAccountDTO.getId()).get();
        mapper.updateEntity(account, updateAccountDTO);
        return mapper.toDTO(accountRepository.save(account));
    }

    @Override
    public void delete(Long id) {
        if (accountRepository.findById(id).isEmpty()) {
            System.out.println("La cuenta con el id " + id + " no se encuentra o no existe.");
        }
        accountRepository.deleteById(id);
    }

}