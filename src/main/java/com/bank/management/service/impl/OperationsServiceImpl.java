package com.bank.management.service.impl;

import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import com.bank.management.entity.Users;
import com.bank.management.repository.AccountRepository;
import com.bank.management.repository.OperationsRepository;
import com.bank.management.repository.UsersRepository;
import com.bank.management.service.OperationsService;
import org.springframework.stereotype.Service;
import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.mapper.OperationsMapper;
import java.util.List;

    @Service
    public class OperationsServiceImpl implements OperationsService {

        private final OperationsRepository operationsRepository;
        private final OperationsMapper mapper;
        private final AccountRepository accountRepository;
        private final UsersRepository usersRepository;

        // CORRECCIÓN: El constructor debe inyectar el OperationsRepository directamente
        // en lugar de la OperationsService.
        public OperationsServiceImpl(OperationsRepository operationsRepository,
                                     OperationsMapper mapper,
                                     AccountRepository accountRepository,
                                     UsersRepository usersRepository) {
            this.operationsRepository = operationsRepository; // Se quitó el casting
            this.mapper = mapper;
            this.accountRepository = accountRepository;
            this.usersRepository = usersRepository;
        }

        @Override
        public OperationsDTO save(CreateOperationsDTO dto) {
            Account account = accountRepository.findById(dto.getAccountId()).get();
            Users users = usersRepository.findById(dto.getUsersId()).get();
            users.setAccount(account);
            usersRepository.save(users);

            // Crear la operacion y asignar las relaciones
            Operations appToSave = mapper.toEntity(dto);
            appToSave.setAccount(account);
            appToSave.setUsers(users);

            return mapper.toDTO(operationsRepository.save(appToSave));
        }

        @Override
        public List<OperationsDTO> getAll() {
            return operationsRepository.findAll()
                    .stream()
                    .map(operations -> mapper.toDTO(operations))
                    .toList();
        }

        @Override
        public OperationsDTO getById(Long id) {
            return mapper.toDTO(operationsRepository.getById(id));
        }

        @Override
        public OperationsDTO update(UpdateOperationsDTO updateOperationsDTO) {
            Operations operations = operationsRepository.findById(updateOperationsDTO.getId()).get();
            // Si el DTO de actualización incluye IDs, necesitarías lógica similar a 'save' aquí.
            // Asumiendo que updateEntity() ahora ignora las relaciones:
            mapper.updateEntity(operations, updateOperationsDTO);
            return mapper.toDTO(operationsRepository.save(operations));
        }

        @Override
        public void delete(Long id) {
            if (operationsRepository.findById(id).isEmpty()) {
                // CORRECCIÓN: Mensaje más apropiado
                System.out.println("La operación con el id " + id + " no se encuentra o no existe.");
            }
            operationsRepository.deleteById(id);
        }

    }