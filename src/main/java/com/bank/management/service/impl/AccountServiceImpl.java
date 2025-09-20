package com.bank.management.service.impl;

import com.bank.management.entity.Account;
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

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public AccountDTO save(CreateAccountDTO createAccountDTO) {
        Account p = mapper.toEntity(createAccountDTO);
        Account accountSaved = accountRepository.save(p);
        AccountDTO accountDTOSaved = mapper.toDTO(accountSaved);
        return accountDTOSaved;

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

}