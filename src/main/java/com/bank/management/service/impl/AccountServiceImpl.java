package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Users;
import com.bank.management.mapper.AccountMapper;
import com.bank.management.repository.AccountRepository;
import com.bank.management.repository.UsersRepository;
import com.bank.management.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;


    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }


    @Override
    public AccountDTO save(CreateAccountDTO createAccountDTO) {
        Account account = mapper.toEntity(createAccountDTO);
        Account accountSaved = accountRepository.save(account);
        return mapper.toDTO(accountSaved);
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AccountDTO getById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cuenta con id " + id + " no existe."));
        return mapper.toDTO(account);
    }

    @Override
    public AccountDTO update(UpdateAccountDTO updateAccountDTO) {
        Account account = accountRepository.findById(updateAccountDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la cuenta con id " + updateAccountDTO.getId()));

        mapper.updateEntity(account, updateAccountDTO); // ✅ ahora sí existe en el mapper
        Account updated = accountRepository.save(account);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. La cuenta con id " + id + " no existe.");
        }
        accountRepository.deleteById(id);
    }
}