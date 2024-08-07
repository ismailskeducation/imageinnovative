package com.imageinnovative.taxcalculation.controller;

import com.imageinnovative.taxcalculation.dto.EmployeeDto;
import com.imageinnovative.taxcalculation.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>("Employee details stored successfully.", HttpStatus.CREATED);
    }

    @GetMapping("/{employeeId}/tax")
    public ResponseEntity<?> getEmployeeTax(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.calculateTax(employeeId));
    }
}