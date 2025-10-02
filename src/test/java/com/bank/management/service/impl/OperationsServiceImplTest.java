package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import com.bank.management.entity.Users;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.mapper.OperationsMapper;
import com.bank.management.repository.OperationsRepository;
import com.bank.management.repository.UsersRepository;
import com.bank.management.service.OperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class OperationsServiceImplTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    private OperationsRepository operationsRepository;
    @Mock
    private OperationsMapper mapper;
    @Mock
    private UsersRepository usersRepository;
    private Operations operationsTest;

    //Se le aplican las pruebas unitarias a la clase OperationsServiceImpl
    private OperationsService operationsService;

    private OperationsDTO operationsDTO;
    private CreateOperationsDTO reateOperationsDTO;
    private Operations operations;

    @BeforeEach
    void setUp() {
        operationsService = new OperationsServiceImpl(operationsRepository,mapper, null, usersRepository);
        operationsTest = new Operations(1L, "Deposito", "credito", null, null);
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
    }

    @Test
    void save_failed() {
    }

    @Test
    void getAll_Success() {
        //2.
        Mockito.when(operationsRepository.findAll()).thenReturn(List.of(operationsTest, operationsTest));

        Mockito.when(mapper.toDTO(operationsTest)).thenAnswer(invocation -> {
            Operations p = invocation.getArgument(0);
            return new OperationsDTO(
                    p.getId(),
                    p.getName(),
                    p.getType(),
                    null,
                    null
            );
        });

        //3.
        var result = operationsService.getAll();

        //4.
        assertEquals( 2, result.size());
        assertNotNull(result);

        //5.
        Mockito.verify(operationsRepository).findAll();
        Mockito.verify(mapper, Mockito.times(2)).toDTO(operationsTest);
    }

    @Test
    void getAlL_Failed_NotResults(){
        //2.
        Mockito.when(operationsRepository.findAll()).thenReturn(new ArrayList<>());

        //3.
        var result = operationsService.getAll();

        //4.
        assertEquals(0, result.size());

        //5.
        Mockito.verify(operationsRepository).findAll();
        Mockito.verifyNoInteractions(mapper);
    }


    @Test
    void getById_Success() {
    }

    @Test
    void getById_Failed_ThrowsExceptions() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}