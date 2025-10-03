package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import com.bank.management.entity.Users;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.exceptions.ResourceNotFoundException;
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

        // 1.
        final Operations testOperation = new Operations(1L, "DEPÓSITO", "Banco", null, null);
        final OperationsDTO expectedDto = new OperationsDTO(1L, "DEPÓSITO", "Banco", null, null);

        //2.
        Mockito.when(operationsRepository.findById(1L)).thenReturn(Optional.of(testOperation));
        Mockito.when(mapper.toDTO(testOperation)).thenReturn(expectedDto);

        //3.
        OperationsDTO result = operationsService.getById(1L);

        //4.
        assertNotNull(result, "El resultado no debe ser nulo.");
        assertEquals(1L, result.getId(), "El ID del DTO debe coincidir.");
        assertEquals(expectedDto.getType(), result.getType(), "El tipo de operación debe coincidir.");

        //5.
        Mockito.verify(operationsRepository, times(1)).findById(1L);
        Mockito.verify(mapper, times(1)).toDTO(testOperation);
    }
    @Test
    void getById_Failed_ThrowsExceptions() {
            //1.
            final Long NON_EXISTENT_ID = 99L;

            //2.
            Mockito.when(operationsRepository.findById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

            //3.

            // 4.
            DataNotFoundException thrown = assertThrows(
                    DataNotFoundException.class,
                    () -> operationsService.getById(NON_EXISTENT_ID),
                    "Debería haberse lanzado una DataNotFoundException"
            );
            String expectedMessagePart = String.format("Resource 'Operations' with id '%d' not found.", NON_EXISTENT_ID);
            assertTrue(thrown.getMessage().contains("Operations"), "El mensaje de la excepción debe mencionar la entidad.");

            //5.
            Mockito.verify(operationsRepository, times(1)).findById(NON_EXISTENT_ID);
            Mockito.verify(mapper, Mockito.never()).toDTO(any());
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
            final UpdateOperationsDTO updateDto = new UpdateOperationsDTO(99L, "Consignacion", "Banco", null, null);

            //2.
            Mockito.when(operationsRepository.findById(99L)).thenReturn(Optional.empty());

            //3.

            //4.
            ResourceNotFoundException thrown = assertThrows(
                    ResourceNotFoundException.class,
                    () -> operationsService.update(updateDto),
                    "Debería haberse lanzado una ResourceNotFoundException"
            );
            assertTrue(thrown.getMessage().contains("No se encontró la cuenta con id 99"),
                    "El mensaje de la excepción debe ser el esperado.");

            // 5.
            Mockito.verify(operationsRepository, times(1)).findById(99L);
            // Las siguientes llamadas NUNCA deben ocurrir si la excepción se lanza
            Mockito.verify(mapper, Mockito.never()).updateEntity(any(), any());
            Mockito.verify(operationsRepository, Mockito.never()).save(any());
            Mockito.verify(mapper, Mockito.never()).toDTO(any());
        }


    @Test
    void delete_shouldCallDeleteById_whenOperationExists() {


            //2.
            Mockito.when(operationsRepository.existsById(1L)).thenReturn(true);
            Mockito.doNothing().when(operationsRepository).deleteById(1L);

            //3.
            operationsService.delete(1L);

            //4.

            //5.
            Mockito.verify(operationsRepository, times(1)).existsById(1L);
            Mockito.verify(operationsRepository, times(1)).deleteById(1L);
        }
    @Test
    void delete_shouldCallDeleteById_whenOperationDoesNotExist() {
            final String expectedMessage = "No se puede eliminar. La operacion con id 999 no existe.";

            //2.
            Mockito.when(operationsRepository.existsById(999L)).thenReturn(false);

            //3.

            //4.
            ResourceNotFoundException thrown = assertThrows(
                    ResourceNotFoundException.class,
                    () -> operationsService.delete(999L),
                    "Debería haberse lanzado ResourceNotFoundException"
            );
            assertEquals(expectedMessage, thrown.getMessage(), "No se puede eliminar. La operacion con id 999 no existe..");

            //5.
            Mockito.verify(operationsRepository, times(1)).existsById(999L);
            Mockito.verify(operationsRepository, Mockito.never()).deleteById(Mockito.anyLong());
        }
}