package com.bank.management.service.impl;

import com.bank.management.dto.request.UpdateOperationsDTO;
import com.bank.management.entity.Account;
import com.bank.management.entity.Operations;
import com.bank.management.entity.Users;
import com.bank.management.exceptions.DataNotFoundException;
import com.bank.management.exceptions.ResourceNotFoundException;
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
            return mapper.toDTO(operationsRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id, "Operations")));
        }

        @Override
        public OperationsDTO update(UpdateOperationsDTO updateOperationsDTO) {
            Operations operations = operationsRepository.findById(updateOperationsDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No se encontró la cuenta con id " + updateOperationsDTO.getId()
                    ));

            mapper.updateEntity(operations, updateOperationsDTO);
            Operations updated = operationsRepository.save(operations);
            return mapper.toDTO(updated);
        }

        @Override
        public void delete(Long id) {
            if (!operationsRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. La operacion con id " + id + " no existe.");
        }
            operationsRepository.deleteById(id);
        }

    }