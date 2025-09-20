package com.bank.management.service;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import java.util.List;

public interface AccountService {
    AccountDTO save(CreateAccountDTO createAccountDTO);
    List<AccountDTO> getAll();
    AccountDTO getById(Long id);

}
