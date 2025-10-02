package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.entity.Account;
import com.bank.management.mapper.AccountMapper;
import com.bank.management.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private AccountDTO accountDTO;
    private CreateAccountDTO createAccountDTO;

    @BeforeEach
    void setUp() {
        // Datos de ejemplo para los tests
        createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setCustomerId(1L);
        createAccountDTO.setAccountNumber("1234567890");
        createAccountDTO.setAccountType("AHORRO");
        createAccountDTO.setSaldo("10000");

        account = new Account();
        account.setId(1L);
        account.setUsersId(1L);
        account.setAccountNumber("1234567890");
        account.setAccountType("AHORRO");
        account.setSaldo("1000");

        accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountNumber("1234567890");
        accountDTO.setAccountType("AHORRO");
        accountDTO.setSaldo("30000");
        accountDTO.setUsersId(1L);
    }

    /* Pasos de prueba:
    * 1. Datos de entrada (Si es necesario, Datos de salida opcionales tambien)
    * 2. Estabelcer los comportamientos simulados necesarios
    * 3. Llamar al metodo a probar
    * 4. Verificar resultados
    * 5. Verificar todas las interacciones con los mocks
    * */

    @Test
    void save_succesful() {
        //2.
        Mockito.when(mapper.toEntity(Mockito.any(CreateAccountDTO.class))).thenReturn(account);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        Mockito.when(mapper.toDTO(Mockito.any(Account.class))).thenReturn(accountDTO);

        //3.
        AccountDTO result = accountService.createAccount(createAccountDTO);

        //4.
        assertNotNull(result);
        assertEquals(accountDTO.getId(), result.getId());
        assertEquals(accountDTO.getAccountNumber(), result.getAccountNumber());

        //5.
        Mockito.verify(accountRepository, times(1)).save(account);
        Mockito.verify(mapper).toEntity(Mockito.any(CreateAccountDTO.class));
        Mockito.verify(accountRepository).save(Mockito.any(Account.class));
        Mockito.verify(mapper).toDTO(Mockito.any(Account.class));
    }

    @Test
    void save_failed() {
        //2.
        Mockito.when(mapper.toEntity(Mockito.any(CreateAccountDTO.class))).thenReturn(account);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        Mockito.when(mapper.toDTO(Mockito.any(Account.class))).thenReturn(accountDTO);

        //3.
        AccountDTO result = accountService.save(createAccountDTO);

        //4
        assertNotNull(result);
        assertEquals(accountDTO.getId(), result.getId());
        assertEquals(createAccountDTO.getAccountNumber(), result.getAccountNumber());

        //5.
        Mockito.verify(accountRepository, times(1)).save(account);
        Mockito.verify(mapper).toEntity(Mockito.any(CreateAccountDTO.class));
        Mockito.verify(accountRepository).save(any(Account.class));
        Mockito.verify(mapper).toDTO(any(Account.class));
    }


    @Test
    void getAll_Success() {
        //2:
        Mockito.when(accountRepository.findAll()).thenReturn(List.of(account));
        Mockito.when(mapper.toDTO(any(Account.class))).thenReturn(accountDTO);

        //3.
        List<AccountDTO> result = accountService.getAll();

        //4.
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(accountDTO.getAccountNumber(), result.get(0).getAccountNumber());

        //5.
        Mockito.verify(accountRepository, times(1)).findAll();
        Mockito.verify(mapper, times(1)).toDTO(any(Account.class));


    }

    @Test
    void getAlL_Failed_NotResults() {
        //2.
        Mockito.when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        //3.
        List<AccountDTO> result = accountService.getAll();

        //4.
        assertNotNull(result);
        assertTrue(result.isEmpty());

        //5.
        Mockito.verify(accountRepository, times(1)).findAll();
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void getById_Success() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(mapper.toDTO(any(Account.class))).thenReturn(accountDTO);

        // Act
        AccountDTO result = accountService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(accountDTO.getId(), result.getId());
    }

    @Test
    void getById_Failed_ThrowsExceptions() {
        //2.
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        //3.
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            accountService.getById(1L);
        });

        //4.
        assertEquals("La cuenta con id 1 no existe.", exception.getMessage());

        //5.
        Mockito.verify(accountRepository, times(1)).findById(1L);
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}