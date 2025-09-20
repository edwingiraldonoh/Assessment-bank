package com.bank.management.service.impl;

import com.bank.management.entity.Operations;
import com.bank.management.service.OperationsService;
import org.springframework.stereotype.Service;
import com.bank.management.dto.request.CreateOperationsDTO;
import com.bank.management.dto.response.OperationsDTO;
import com.bank.management.repository.OperationsRepository;
import com.bank.management.mapper.OperationsMapper;
import java.util.List;


@Service
public class OperationsServiceImpl implements OperationsService {

    private final OperationsRepository operationsRepository;
    private final OperationsMapper mapper;

    public OperationsServiceImpl(OperationsRepository operationsRepository, OperationsMapper mapper) {
        this.operationsRepository = operationsRepository;
        this.mapper = mapper;
    }

    @Override
    public OperationsDTO save(CreateOperationsDTO createOperationsDTO) {
        Operations p = mapper.toEntity(createOperationsDTO);
        Operations operationsSaved = operationsRepository.save(p);
        OperationsDTO operationsDTOSaved = mapper.toDTO(operationsSaved);
        return operationsDTOSaved;

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

}
