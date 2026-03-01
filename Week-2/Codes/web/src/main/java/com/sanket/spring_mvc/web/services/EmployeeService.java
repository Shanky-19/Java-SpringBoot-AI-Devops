package com.sanket.spring_mvc.web.services;

import com.sanket.spring_mvc.web.dto.EmployeeDTO;
import com.sanket.spring_mvc.web.entities.EmployeeEntity;
import com.sanket.spring_mvc.web.repositories.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = mapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return mapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        boolean exists = isExists(employeeId);
        if(!exists) {
            return false;
        }
        employeeRepository.deleteById(employeeId);
        return true;
    }


    public EmployeeDTO updateEmployeeDetails(Long employeeId, Map<String, Object> updates) {
        boolean exists = isExists(employeeId);
        if(!exists) {
            return null;
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        // now to update employee field -> we will use reflection API
        updates.forEach((key, val) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key);
            assert fieldToBeUpdated != null;
            fieldToBeUpdated.setAccessible(true); // to make private field as public
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, val);
        });
        return mapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }

    private boolean isExists(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
