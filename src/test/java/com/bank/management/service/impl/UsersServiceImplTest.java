package com.bank.management.service.impl;

import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Users;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.exceptions.DuplicatedDataException;
import com.bank.management.mapper.UsersMapper;
import com.bank.management.repository.UsersRepository;
import com.bank.management.service.UsersService;
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

class UsersServiceImplTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UsersMapper mapper;

    private Users usersTest;


    //Se le aplican las pruebas unitarias a la clase UsersServiceImpl
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        usersService = new UsersServiceImpl(usersRepository, mapper);
        usersTest = new Users(1L, "12345678", "John", "John@gmail.com","12345678", new Account(),  new ArrayList<>());
    }

     /* Pasos de prueba:
     * 1. Datos de entrada (Si es necesario, Datos de salida opcionales tambien)
     * 2. Estabelcer los comportamientos simulados necesarios
     * 3. Llamar al metodo a probar
     * 4. Verificar resultados
     * 5. Verificar todos las interacciones con los mocks
     * */

    @Test

    void save_succesful() {
        //1.
        Users pTest2 = new Users(null, usersTest.getDni(), usersTest.getName(), usersTest.getEmail(), usersTest.getPassword(), new Account(), new ArrayList<>());

        //2.
        Mockito.when(mapper.toEntity(Mockito.any(CreateUsersDTO.class))).thenReturn(pTest2);
        Mockito.when(usersRepository.existsByDni(Mockito.any(String.class))).thenReturn(false);
        Mockito.when(usersRepository.save(Mockito.any(Users.class))).thenAnswer(invocation -> {
            Users p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });
        Mockito.when(mapper.toDTO(Mockito.any(Users.class))).thenAnswer(invocation -> {
            Users p = invocation.getArgument(0);
            return new UsersDTO(
                    p.getId(),
                    p.getDni(),
                    p.getName(),
                    p.getEmail(),
                    p.getPassword(),
                    p.getAccount().getId()
            );
        });

        //3.
        var result = usersService.save(new CreateUsersDTO(
                usersTest.getDni(),
                usersTest.getName(),
                usersTest.getEmail(),
                usersTest.getPassword()));

        //4.
        assertAll("Users saved",
                () -> assertInstanceOf(UsersDTO.class, result),
                () -> assertEquals(1L, result.getId())
        );

        //5.
        Mockito.verify(mapper).toEntity(Mockito.any(CreateUsersDTO.class));
        Mockito.verify(usersRepository).existsByDni(Mockito.any(String.class));
        Mockito.verify(usersRepository).save(Mockito.any(Users.class));
        Mockito.verify(mapper).toDTO(Mockito.any(Users.class));
    }

    @Test
    void save_failed() {

        //2.
        Mockito.when(usersRepository.existsByDni(Mockito.any(String.class))).thenReturn(true);

        //3.
        var result = assertThrows(DuplicatedDataException.class, () -> usersService.save(new CreateUsersDTO(
                usersTest.getDni(),
                usersTest.getName(),
                usersTest.getEmail(),
                usersTest.getPassword())));

        //4.
        assertInstanceOf(DuplicatedDataException.class, result);

        //5.
        Mockito.verifyNoInteractions(mapper);
        Mockito.verify(usersRepository).existsByDni(Mockito.any(String.class));


    }

    @Test
    void getAll_Success() {
        /*//1.
        List<UsersDTO> users  = new ArrayList<>();
        users.add(new UsersDTO());
        users.add(new UsersDTO());*/

        //2.
            Mockito.when(usersRepository.findAll()).thenReturn(List.of(usersTest, usersTest));

        Mockito.when(mapper.toDTO(usersTest)).thenAnswer(invocation -> {
            Users p = invocation.getArgument(0);
            return new UsersDTO(
                    p.getId(),
                    p.getDni(),
                    p.getName(),
                    p.getEmail(),
                    p.getPassword(),
                    p.getAccount().getId()
            );
        });

        //3.
        var result = usersService.getAll();

        //4.
        assertEquals( 2, result.size());
        assertNotNull(result);

        //5.
         Mockito.verify(usersRepository).findAll();
         Mockito.verify(mapper, Mockito.times(2)).toDTO(usersTest);
    }

    @Test
    void getAlL_Failed_NotResults(){
        //2.
        Mockito.when(usersRepository.findAll()).thenReturn(new ArrayList<>());

        //3.
        var result = usersService.getAll();

        //4.
        assertEquals(0, result.size());

        //5.
        Mockito.verify(usersRepository).findAll();
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void getById_Success() {

        //1.
        UsersDTO pDTO = new UsersDTO(1L, "12345678", "John", "John@gmail.com", "12345678", new Account().getId());

        //2.
        Mockito.when(usersRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(usersTest));
        Mockito.when(mapper.toDTO(any(Users.class))).thenAnswer(invocation -> {
            Users p = invocation.getArgument(0);
            return new UsersDTO(
                    p.getId(),
                    p.getDni(),
                    p.getName(),
                    p.getEmail(),
                    p.getPassword(),
                    p.getAccount().getId()
            );
        });

        //3.
        var result = usersService.getById(1L);

        //4.
        assertAll("Users Found",
                () -> assertNotNull(result),
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals(pDTO.getDni(), result.getDni()),
                () -> assertEquals(pDTO.getName(), result.getName()),
                () -> assertEquals(pDTO.getEmail(), result.getEmail()),
                () -> assertEquals(pDTO.getPassword(), result.getPassword()),
                () -> assertEquals(pDTO.getAccount(), result.getAccount())
        );

        //5.
        Mockito.verify(usersRepository).findById(any(Long.class));
        Mockito.verify(mapper).toDTO(any(Users.class));
    }

    @Test
    void getById_Failed_ThrowsExceptions() {
        //2.
        Mockito.when(usersRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        //3.
        var result = assertThrows(DataNotFoundException.class, () -> usersService.getById(1L));

        //4.
        assertInstanceOf(DataNotFoundException.class, result);

        //5.
        Mockito.verify(usersRepository).findById(Mockito.any(Long.class));
        Mockito.verifyNoInteractions(mapper);

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}