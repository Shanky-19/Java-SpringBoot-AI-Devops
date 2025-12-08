package com.sanket.spring_mvc.web.controllers;

import com.sanket.spring_mvc.web.dto.EmployeeDTO;
import com.sanket.spring_mvc.web.entities.EmployeeEntity;
import com.sanket.spring_mvc.web.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    // contructor ingestion
    private final EmployeeRepository employeeRepository;
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/getSecretMessage")
    public String getMySuperSecretMessage() {
        return "Secret Message : sufnwe35r382hjsf";
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        // Here jackson is converting my java object (dto) back to json
        // jackson is added when we add dependency of SpringWeb
        return new EmployeeDTO(id, "Sanket", "sanket@gmail.com", 24, LocalDate.of(2024, 11, 19), true);
    }

//    @GetMapping("/{employeeId}")
//    public EmployeeDTO getEmployeeById2(@PathVariable Long employeeId) {
//        return new EmployeeDTO(employeeId, "Sanket", "sanket@gmail.com", 24, LocalDate.of(2024, 11, 19), true);
//    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployees(@RequestParam Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return employeeRepository.findAll();
    }

    @PostMapping
    public String createNewEmployee() {
        return "Hello from post";
    }

    @PostMapping("/requestBodyEg")
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployeeDTO) {
        inputEmployeeDTO.setId(100L);
        return inputEmployeeDTO;
    }

    @PutMapping
    public String updateEmployeeById() {
        return "Hello from put";
    }

    @GetMapping("/v2/{employeeId}")
    public Optional<EmployeeEntity> getEmployeeById2(@PathVariable(name = "employeeId") Long id) {
        // Here jackson is converting my java object (dto) back to json
        return employeeRepository.findById(id);
    }

    @PostMapping("/save")
    public EmployeeEntity saveNewEmployee(@RequestBody EmployeeEntity inputEmployeeEntity) {
        return employeeRepository.save(inputEmployeeEntity);
    }

}
