package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateAccountDTO;
import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.dto.response.AccountDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import com.bank.management.entity.Users;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.mapper.OperationsMapper;
import com.bank.management.repository.AccountRepository;
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
import java.util.NoSuchElementException;
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
    @Mock
    private AccountRepository accountRepository;
    private Operations operationsTest;

    //Se le aplican las pruebas unitarias a la clase OperationsServiceImpl
    private OperationsService operationsService;

    private OperationsDTO operationsDTO;
    private CreateOperationsDTO createOperationsDTO;
    private UpdateOperationsDTO updateOperationsDTO;
    private Operations operations;

    @BeforeEach
    void setUp() {
        operationsService = new OperationsServiceImpl(operationsRepository,mapper, accountRepository, usersRepository);
        operationsTest = new Operations(1L, "Deposito", "credito", null, null);
        updateOperationsDTO = new UpdateOperationsDTO(1l, "Extraccion", "Extraccion de dinero", null, null);
        operationsDTO = new OperationsDTO(
                updateOperationsDTO.getId(),
                updateOperationsDTO.getName(),
                updateOperationsDTO.getType(),
                updateOperationsDTO.getUsersId(),
                updateOperationsDTO.getAccount()
        );
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
        // 1.
        final Long accountId = 2L;
        final Long userId = 3L;
        Account accountTest = new Account(accountId, "123748454865 22", "Credito", "1000000", null, null );
        Users usersTest = new Users(userId, "1010101010", "edwin", "edwin@gmail.com", "12345678", null, null);
        createOperationsDTO = new CreateOperationsDTO("Consulta", "Consulta de saldo", userId, accountId);

        Operations operationToSave = new Operations(
                null, // ID nulo al crear
                createOperationsDTO.getName(),
                createOperationsDTO.getType(),
                accountTest,
                usersTest

        );
        Operations savedOperation = new Operations(
                1L, // ID simulado al guardar
                createOperationsDTO.getName(),
                createOperationsDTO.getType(),
                accountTest,
                usersTest

        );

        // 2.
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountTest));
        Mockito.when(usersRepository.findById(userId)).thenReturn(Optional.of(usersTest));
        Mockito.when(usersRepository.save(any(Users.class))).thenReturn(usersTest);
        Mockito.when(mapper.toEntity(any(CreateOperationsDTO.class))).thenReturn(operationToSave);
        Mockito.when(operationsRepository.save(any(Operations.class))).thenReturn(savedOperation);

        // Mock para el DTO de retorno
        OperationsDTO expectedDTO = new OperationsDTO(
                savedOperation.getId(),
                savedOperation.getName(),
                savedOperation.getType(),
                savedOperation.getUsers().getId(),
                null // O un objeto AccountDTO si el mapper lo maneja
        );
        Mockito.when(mapper.toDTO(savedOperation)).thenReturn(expectedDTO);

        // 3. Llamar al metodo a probar
        OperationsDTO result = operationsService.save(createOperationsDTO);

        // 4. Verificar resultados
        assertAll("Operación guardada exitosamente",
                () -> assertNotNull(result),
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Consulta", result.getName())
        );

        // 5.
        Mockito.verify(accountRepository).findById(accountId);
        Mockito.verify(usersRepository).findById(userId);
        Mockito.verify(usersRepository).save(any(Users.class));
        Mockito.verify(mapper).toEntity(createOperationsDTO);
        Mockito.verify(operationsRepository).save(any(Operations.class));
        Mockito.verify(mapper).toDTO(savedOperation);
    }
    @Test
    void save_failed_AccountNotFound() {
        //1.
        final Long id = 99L;
        final Long userId = 3L;
        Users usersTest = new Users(userId, "TestUser", "Apellido", "dni", "password", null, null);
        createOperationsDTO = new CreateOperationsDTO("Transferencia", "debito", userId, id);

        //2.
        Mockito.when(accountRepository.findById(id)).thenReturn(Optional.empty());

        //3.
        assertThrows(NoSuchElementException.class, () -> operationsService.save(createOperationsDTO));

        //4.

        //5.
        Mockito.verify(accountRepository).findById(id);
        Mockito.verify(usersRepository, Mockito.never()).findById(any());
        Mockito.verify(usersRepository, Mockito.never()).save(any());
        Mockito.verify(mapper, Mockito.never()).toEntity(any());
        Mockito.verify(operationsRepository, Mockito.never()).save(any());
    }
    @Test
    void save_failed_UserNotFound() {
        //1.
        final Long accountId = 2L;
        final Long id = 99L;
        Account accountTest = new Account(accountId, "Caja de Ahorro", "ARS", null, null, null);
        createOperationsDTO = new CreateOperationsDTO("Transferencia", "debito", id, accountId);

        //2.
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountTest));
        Mockito.when(usersRepository.findById(id)).thenReturn(Optional.empty());

        //3.
        assertThrows(NoSuchElementException.class, () -> operationsService.save(createOperationsDTO));

        //5.
        Mockito.verify(accountRepository).findById(accountId);
        Mockito.verify(usersRepository).findById(id);
        Mockito.verify(usersRepository, Mockito.never()).save(any());
        Mockito.verify(mapper, Mockito.never()).toEntity(any());
        Mockito.verify(operationsRepository, Mockito.never()).save(any());
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
        //1.
        Long id = operationsTest.getId();
        OperationsDTO expectedDTO = new OperationsDTO(
                operationsTest.getId(),
                operationsTest.getName(),
                operationsTest.getType(),
                null,
                null
        );

        //2.
        Mockito.when(operationsRepository.getById(id)).thenReturn(operationsTest);
        Mockito.when(mapper.toDTO(operationsTest)).thenReturn(expectedDTO);

        // 3. Llamar al metodo a probar
        OperationsDTO result = operationsService.getById(id);

        // 4. Verificar resultados
        assertAll("Operación obtenida exitosamente",
                () -> assertNotNull(result),
                () -> assertEquals(id, result.getId()),
                // Comparamos el resultado con el DTO esperado, no con el objeto Entity (operationsTest)
                () -> assertEquals(expectedDTO.getName(), result.getName())
        );

        // 5. Verificar todas las interacciones con los mocks
        Mockito.verify(operationsRepository).getById(id);
        Mockito.verify(mapper).toDTO(operationsTest);
    }
    @Test
    void getById_Failed_ThrowsExceptions() {
        // 1. Datos de entrada
        Long nonExistentId = 99L;

        // 2. Establecer los comportamientos simulados necesarios
        // Mockito.when(operationsRepository.getById(nonExistentId)).thenThrow(new EntityNotFoundException("No se encontró"));
        // NOTA: operationsRepository.getById(id) en Spring Data JPA puede lanzar una
        // org.hibernate.LazyInitializationException o devolver un proxy si no se encuentra.
        // Para simular el fallo al intentar acceder al objeto (si el mock es estricto),
        // o para cubrir una excepción común en Spring Data/Hibernate:
        Mockito.when(operationsRepository.getById(nonExistentId)).thenThrow(new RuntimeException("Simulated error when accessing not found entity"));


        // 3. Llamar al metodo a probar y verificar excepción
        // La excepción exacta depende del comportamiento real de 'getById' y el entorno Spring,
        // pero una RuntimeException genérica es segura para la prueba de que algo falla.
        assertThrows(RuntimeException.class, () -> operationsService.getById(nonExistentId));

        // 4. Verificación no necesaria ya que la excepción es la verificación

        // 5. Verificar todas las interacciones con los mocks
        Mockito.verify(operationsRepository).getById(nonExistentId);
        Mockito.verifyNoInteractions(mapper);
    }


    @Test
    void update_shouldReturnUpdatedOperationsDTO() {
        //2.
        Mockito.when(operationsRepository.findById(updateOperationsDTO.getId())).thenReturn(Optional.of(operationsTest));
        Mockito.doNothing().when(mapper).updateEntity(any(Operations.class), Mockito.eq(updateOperationsDTO));
        Mockito.when(operationsRepository.save(any(Operations.class))).thenReturn(operationsTest);
        Mockito.when(mapper.toDTO(operationsTest)).thenReturn(operationsDTO);

        //3.
        OperationsDTO result = operationsService.update(updateOperationsDTO);

        //4.
        assertAll("Operationes actualizadas correctamente.",
                () -> assertNotNull(result),
                () -> assertEquals(operationsDTO.getId(), result.getId()),
                () -> assertEquals(operationsDTO.getName(), result.getName()),
                () -> assertEquals(operationsDTO.getType(), result.getType())
        );

        //5.
        Mockito.verify(operationsRepository).findById(updateOperationsDTO.getId());
        Mockito.verify(mapper).updateEntity(operationsTest, updateOperationsDTO);
        Mockito.verify(operationsRepository).save(operationsTest);
        Mockito.verify(mapper).toDTO(operationsTest);
    }
    @Test
    void update_shouldThrowNoSuchElementException() {
        //1.
        Mockito.when(operationsRepository.findById(updateOperationsDTO.getId())).thenReturn(Optional.empty());

        // .
        assertThrows(NoSuchElementException.class, () -> operationsService.update(updateOperationsDTO));

        // 4. Verificación no necesaria.

        //5.
        Mockito.verify(operationsRepository).findById(updateOperationsDTO.getId());
        Mockito.verify(mapper, Mockito.never()).updateEntity(any(), any());
        Mockito.verify(operationsRepository, Mockito.never()).save(any());
    }


    @Test
    void delete_shouldCallDeleteById_whenOperationExists() {
        // 1.
        Long id = 1L;

        // 2.
        Mockito.when(operationsRepository.findById(id)).thenReturn(Optional.of(operationsTest));
        Mockito.doNothing().when(operationsRepository).deleteById(id);

        // 3.
        operationsService.delete(id);

        // 4. Verificar resultados (No hay retorno en un método void)

        // 5.
        Mockito.verify(operationsRepository).findById(id);
        Mockito.verify(operationsRepository).deleteById(id);
        Mockito.verifyNoInteractions(mapper);
    }
    @Test
    void delete_shouldCallDeleteById_whenOperationDoesNotExist() {
        // 1.
        Long id = 99L;

        // 2.
        Mockito.when(operationsRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.doNothing().when(operationsRepository).deleteById(id);

        // 3.
        operationsService.delete(id);

        // 4. Verificar resultados

        // 5.
        Mockito.verify(operationsRepository).findById(id);
        Mockito.verify(operationsRepository).deleteById(id);
        Mockito.verifyNoInteractions(mapper);
    }
}