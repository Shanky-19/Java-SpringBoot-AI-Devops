package com.sanket.spring_mvc.web.controllers;

import com.sanket.spring_mvc.web.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

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
    public String getAllEmployees(@RequestParam Integer age,
                                  @RequestParam(required = false) String sortBy) {
        return "Hi age : " + age;
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
}
