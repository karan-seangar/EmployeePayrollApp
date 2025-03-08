package com.spring.employeePayroll.controller;

import com.spring.employeePayroll.dto.EmployeePayrollDTO;
import com.spring.employeePayroll.dto.ResponseDTO;
import com.spring.employeePayroll.model.Employee;
import com.spring.employeePayroll.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Get all employees
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllEmployees() {
        List<EmployeePayrollDTO> employees = service.getAllEmployees();
        return ResponseEntity.ok(new ResponseDTO("Fetched all employees successfully", employees));
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeePayrollDTO> employee = service.getEmployeeById(id);
        return employee.map(emp -> ResponseEntity.ok(new ResponseDTO("Employee found", emp)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ResponseDTO("Employee not found", null)));
    }

    // Create employee
    @PostMapping
    public ResponseEntity<ResponseDTO> createEmployee(@Valid @RequestBody EmployeePayrollDTO employee) {
        Employee emp = service.saveEmployee(employee);
        return ResponseEntity.ok(new ResponseDTO("Employee created successfully", emp));
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeePayrollDTO updatedEmployee) {
        Optional<EmployeePayrollDTO> existingEmployee = service.getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            Employee updated = service.updateEmployee(id, updatedEmployee);
            return ResponseEntity.ok(new ResponseDTO("Employee updated successfully", updated));
        }
        return ResponseEntity.status(404).body(new ResponseDTO("Employee not found", null));
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable Long id) {
        Optional<EmployeePayrollDTO> employee = service.getEmployeeById(id);
        if (employee.isPresent()) {
            service.deleteEmployee(id);
            return ResponseEntity.ok(new ResponseDTO("Employee deleted successfully", null));
        }
        return ResponseEntity.status(404).body(new ResponseDTO("Employee not found", null));
    }
}