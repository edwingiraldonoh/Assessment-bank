package com.bank.management.controller;

import com.bank.management.dto.request.CreateUsersDTO;
import com.bank.management.dto.response.UsersDTO;
import com.bank.management.entity.Account;
import com.bank.management.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UsersService usersService;

    private UsersController usersController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        usersController = new UsersController(usersService);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        objectMapper = new ObjectMapper();
    }

    /* Pasos de prueba:
     * 1. Datos de entrada (Si es necesario, Datos de salida opcionales tambien)
     * 2. Estabelcer los comportamientos simulados necesarios
     * 3. Llamar al metodo a probar
     * 4. Verificar resultados
     * 5. Verificar todas las interacciones con los mocks
     * */

    //GetUsersAll
    @Test
    void getUsers_shouldReturnContent  () throws Exception {
        //1.
        List<UsersDTO> Users = new ArrayList<>();
        Users.add(new UsersDTO(1L, "1010101010", "Edwin", "Edwingiraldo@gamil.com", "12344567", new Account().getId()));
        Users.add(new UsersDTO(2L, "2020202020", "Carlos", "Carlos@gmail.com", "12345678", new Account().getId()));

        //2.
        Mockito.when(usersService.getAll()).thenReturn(Users);

        //3 & 4.
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        //5.
        Mockito.verify(usersService).getAll();
    }
    @Test
    void getUsers_ShouldReturnEmpty() throws Exception {
        //1.
        List<UsersDTO> users = new ArrayList<>();

        //2.
        Mockito.when(usersService.getAll()).thenReturn(users);

        //3 & 4.
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        //5.
        Mockito.verify(usersService).getAll();
    }


    @Test
    void saveUsers(){
    }

    //GetUsersByID
    @Test
    void getUsersById() {
    }

    @Test
    void updateUsers() {
    }

    @Test
    void deleteUsers() {
    }
}