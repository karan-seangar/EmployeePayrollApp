package com.spring.employeePayroll.controller;

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
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = service.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create employee
    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee updatedEmployee) {
        Optional<Employee> existingEmployee = service.getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            updatedEmployee.setId(id);
            return ResponseEntity.ok(service.saveEmployee(updatedEmployee));
        }
        return ResponseEntity.notFound().build();
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = service.getEmployeeById(id);
        if (employee.isPresent()) {
            service.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
