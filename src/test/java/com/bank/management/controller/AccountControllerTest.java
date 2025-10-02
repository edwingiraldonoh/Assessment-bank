package com.bank.management.controller;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Account;
import com.bank.management.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    private AccountController accountController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        accountController = new AccountController(accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();

        /* Pasos de prueba:
         * 1. Datos de entrada (Si es necesario, Datos de salida opcionales tambien)
         * 2. Estabelcer los comportamientos simulados necesarios
         * 3. Llamar al metodo a probar
         * 4. Verificar resultados
         * 5. Verificar todas las interacciones con los mocks
         * */
    }

    @Test
    void getAccount() {
    }

    @Test
    void saveAccount_success() throws Exception{
        //1.
        CreateAccountDTO createAccountDTO = new CreateAccountDTO("1234567891", "Carlos", "20000", null);
        AccountDTO accountDTO = new AccountDTO(1L, createAccountDTO.getAccountNumber(), createAccountDTO.getAccountType(), createAccountDTO.getSaldo(), null);

        //2.
        Mockito.when(accountService.save(Mockito.any(CreateAccountDTO.class))).thenReturn(accountDTO);

        //3 & 4.
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("1234567891"));
        //5.
        Mockito.verify(accountService).save(Mockito.any(CreateAccountDTO.class));
    }

    @Test
    void getOperationsById() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}