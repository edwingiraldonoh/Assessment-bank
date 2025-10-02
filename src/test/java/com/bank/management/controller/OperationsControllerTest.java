package com.bank.management.controller;

import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.service.OperationsService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OperationsControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OperationsService operationsService;

    private OperationsController operationsController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        operationsController = new OperationsController(operationsService);
        mockMvc = MockMvcBuilders.standaloneSetup(operationsController).build();
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
    void getOperations() {
    }

    @Test
    void saveOperations() {
    }



    //saveUsers
    @Test
    void saveUsers_success() throws Exception{
        //1.
        CreateOperationsDTO createOperationsDTO = new CreateOperationsDTO("Consulta", "Extraccion de saldo", null, null);
        OperationsDTO operationsDTO = new OperationsDTO(1L, createOperationsDTO.getName(), createOperationsDTO.getType(), null, null);

        //2.
        Mockito.when(operationsService.save(Mockito.any(CreateOperationsDTO.class))).thenReturn(operationsDTO);

        //3 & 4.
        mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOperationsDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
        //5.
        Mockito.verify(operationsService).save(Mockito.any(CreateOperationsDTO.class));
    }
    @Test
    void saveUsers_failure_invalidData() throws Exception{
        //1.
        CreateOperationsDTO createOperationsDTO = new CreateOperationsDTO("", "Extraccion de saldo", null, null);

        //2.

        //3 & 4.
        mockMvc.perform(post("/operations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOperationsDTO)))
                .andExpect(status().isBadRequest());

        //5.
        Mockito.verify(operationsService, Mockito.never()).save(Mockito.any(CreateOperationsDTO.class));
    }



    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}