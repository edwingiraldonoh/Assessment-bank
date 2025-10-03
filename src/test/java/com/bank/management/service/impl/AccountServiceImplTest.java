package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.UpdateAccountDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.entity.Account;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.exceptions.ResourceNotFoundException;
import com.bank.management.mapper.AccountMapper;
import com.bank.management.repository.AccountRepository;
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
    private UpdateAccountDTO updateAccountDTO;

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

        updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setId(1L);
        updateAccountDTO.setAccountNumber("1343536564");
        updateAccountDTO.setAccountType("CORRIENTE");
        updateAccountDTO.setSaldo("3000");
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
            // 1.
            Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            // 2.
            DataNotFoundException thrown = assertThrows(
                    DataNotFoundException.class,
                    () -> accountService.getById(1L),
                    "Debería haberse lanzado una DataNotFoundException"
            );

            // 3.
            String expectedMessage = String.format("Resource 'Account' with id '%d' not found.", 1L);
            assertTrue(thrown.getMessage().contains("Account"), "El mensaje de la excepción debe indicar la entidad.");

            // 4.

            // 5.
            Mockito.verify(accountRepository, times(1)).findById(1L);
            Mockito.verify(mapper, never()).toDTO(Mockito.any());
        }


    @Test
    void update_ShouldReturnUpdatedAccountDTO() {
        //2.
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(mapper.toDTO(any(Account.class))).thenReturn(accountDTO);

        //3.
        AccountDTO result = accountService.update(updateAccountDTO);

        //4.

        //5.
        assertNotNull(result);
        verify(mapper, times(1)).updateEntity(account, updateAccountDTO);
        verify(accountRepository, times(1)).save(account);
    }
    @Test
    void update_ShouldThrowEntityNotFoundException() {
            //2.
            Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.empty());

            //3.
            ResourceNotFoundException thrown = assertThrows(
                    ResourceNotFoundException.class,
                    () -> accountService.update(updateAccountDTO),
                    "Debería haberse lanzado una ResourceNotFoundException"
            );

            //3.
            assertTrue(thrown.getMessage().contains("No se encontró la cuenta con id 1"),
                    "El mensaje debe ser el esperado.");

            //4.

            //5.
            Mockito.verify(accountRepository, times(1)).findById(1L); // Solo se debe llamar a findById
            Mockito.verify(mapper, never()).updateEntity(any(), Mockito.any());
            Mockito.verify(accountRepository, never()).save(Mockito.any());
        }


    @Test
    void delete_ShouldSuccess() {
        //2.
        Mockito.when(accountRepository.existsById(1L)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(1L);

        //3.
        assertDoesNotThrow(() -> accountService.delete(1L));

        //4.

        //5.
        Mockito.verify(accountRepository, times(1)).deleteById(1L);
    }
    @Test
    void delete_ShouldNotFoundException() {
            //1.
            final Long TEST_ID = 99L;
            final String expectedMessage = "No se puede eliminar. La cuenta con id 99 no existe.";

            //2.
            Mockito.when(accountRepository.existsById(TEST_ID)).thenReturn(false);

            //3.

            //4.
            ResourceNotFoundException thrown = assertThrows(
                    ResourceNotFoundException.class,
                    () -> accountService.delete(TEST_ID),
                    "Debería haberse lanzado ResourceNotFoundException"
            );
            assertEquals(expectedMessage, thrown.getMessage(), "El mensaje de la excepción debe ser correcto.");

            //5.
            Mockito.verify(accountRepository, times(1)).existsById(TEST_ID);
            Mockito.verify(accountRepository, never()).deleteById(anyLong());
        }
}