package com.sanket.spring_mvc.web.services;

import com.sanket.spring_mvc.web.dto.EmployeeDTO;
import com.sanket.spring_mvc.web.entities.EmployeeEntity;
import com.sanket.spring_mvc.web.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    // contructor ingestion
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public List<EmployeeDTO> findAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO findById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        EmployeeDTO employeeDTO = mapper.map(employeeEntity, EmployeeDTO.class);
        return employeeDTO;
    }

    public EmployeeDTO save(EmployeeDTO inputEmployeeDTO) {
        EmployeeEntity inputEmployeeEntity = mapper.map(inputEmployeeDTO, EmployeeEntity.class);
        EmployeeEntity saveEmployeeEntity = employeeRepository.save(inputEmployeeEntity);
        return mapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }
}
