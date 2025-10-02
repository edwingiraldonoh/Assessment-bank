package com.bank.management.controller;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Account;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.exceptions.GlobalExceptionHandler;
import com.bank.management.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        accountController = new AccountController(accountService);
        globalExceptionHandler = new GlobalExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).setControllerAdvice(globalExceptionHandler).build();
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
    void saveAccount() {
    }


    //GetAccountById
    @Test
    void getAccountById_success() throws Exception {
        //1.
        AccountDTO p = (new AccountDTO(1L, "378453478563 33", "Debito", "100000", null));

        //2.
        Mockito.when(accountService.getById(Mockito.any(Long.class))).thenReturn(p);

        //3 & 4.
        mockMvc.perform(get("/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("378453478563 33"));

        //5.
        Mockito.verify(accountService).getById(Mockito.any(Long.class));
    }
    @Test
    void getAccount_failure_notFound() throws Exception{
        //1.
        Long id = 99L;

        //2.
        Mockito.when(accountService.getById(Mockito.eq(id)))
                .thenThrow(new DataNotFoundException(id, "Account not found with ID: " + id));

        //3 & 4.
        mockMvc.perform(get("/account/" + id))
                .andExpect(status().isNotFound()) // Ahora el Controller lo convierte a 404
                .andExpect(jsonPath("$.message").value("Account not found with ID: 99 con id 99 no existe"));

        //5.
        Mockito.verify(accountService).getById(Mockito.eq(id));
    }



    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}