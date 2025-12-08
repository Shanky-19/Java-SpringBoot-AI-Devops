package com.sanket.spring_mvc.web.controllers;

import com.sanket.spring_mvc.web.dto.EmployeeDTO;
import com.sanket.spring_mvc.web.entities.EmployeeEntity;
import com.sanket.spring_mvc.web.repositories.EmployeeRepository;
import com.sanket.spring_mvc.web.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    // contructor ingestion
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return employeeService.findAll();
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
    public EmployeeDTO getEmployeeById2(@PathVariable(name = "employeeId") Long id) {
        // Here jackson is converting my java object (dto) back to json
        return employeeService.findById(id);
    }

    @PostMapping("/save")
    public EmployeeDTO saveNewEmployee(@RequestBody EmployeeDTO inputEmployeeDTO) {
        return employeeService.save(inputEmployeeDTO);
    }

}
