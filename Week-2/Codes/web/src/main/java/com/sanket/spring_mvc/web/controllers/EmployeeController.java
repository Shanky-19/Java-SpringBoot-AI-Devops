package com.sanket.spring_mvc.web.controllers;

import com.sanket.spring_mvc.web.dto.EmployeeDTO;
import com.sanket.spring_mvc.web.exceptions.ResourceNotFoundException;
import com.sanket.spring_mvc.web.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

//    @GetMapping("/{employeeId}")
//    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
//        // Here jackson is converting my java object (dto) back to json
//        // jackson is added when we add dependency of SpringWeb
//        return new EmployeeDTO(id, "Sanket", "sanket@gmail.com", 24, LocalDate.of(2024, 11, 19), true);
//    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
        if(employeeDTO == null) {
            throw new ResourceNotFoundException("Employee not found with id : " + employeeId);
        }
        return ResponseEntity.ok(employeeDTO);
    }

     // Best practice is to add these in Global exception handler

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception) {
//        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
//    }


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    public String createNewEmployee() {
        return "Hello from post";
    }

    @PostMapping("/requestBodyEg")
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployeeDTO) {
        EmployeeDTO createdEmployeeDTO = employeeService.createNewEmployee(inputEmployeeDTO);
        return new ResponseEntity<>(createdEmployeeDTO, HttpStatus.CREATED);
    }

    @GetMapping("/v2/{employeeId}")
    public EmployeeDTO getEmployeeById2(@PathVariable(name = "employeeId") Long id) {
        // Here jackson is converting my java object (dto) back to json
        return employeeService.findById(id);
    }

    @PostMapping("/save")
    public EmployeeDTO saveNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployeeDTO) {
        return employeeService.save(inputEmployeeDTO);
    }

    // PUT -> When entire existing object needs to be changed/replaced
    // PATCH -> When few fields in existing object needs to be updated

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,
                                                          @PathVariable Long employeeId) {

        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean deleted = employeeService.deleteEmployeeById(employeeId);
        if(deleted) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> patchEmployeeById(@PathVariable Long employeeId,
                                  @RequestBody Map<String, Object> updates) {
        EmployeeDTO employeeDTO = employeeService.updateEmployeeDetails(employeeId, updates);
        if(employeeDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeDTO);
    }

}
