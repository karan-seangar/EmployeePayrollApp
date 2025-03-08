package com.spring.employeePayroll.service;

import com.spring.employeePayroll.dto.EmployeePayrollDTO;
import com.spring.employeePayroll.model.Employee;
import com.spring.employeePayroll.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // Get all employees and convert to DTO manually
    public List<EmployeePayrollDTO> getAllEmployees() {
        List<Employee> employees = repository.findAll();
        List<EmployeePayrollDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOList.add(new EmployeePayrollDTO(employee.getName(), employee.getSalary(), employee.getDepartment()));
        }
        return employeeDTOList;
    }

    // Get an employee by ID and convert it manually
    public Optional<EmployeePayrollDTO> getEmployeeById(Long id) {
        Optional<Employee> employee = repository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            return Optional.of(new EmployeePayrollDTO(emp.getName(), emp.getSalary(), emp.getDepartment()));
        }
        return Optional.empty();
    }

    // Save a new employee
    public Employee saveEmployee(EmployeePayrollDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        return repository.save(employee);
    }

    // Delete employee by ID
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    public Employee updateEmployee(Long id, @Valid EmployeePayrollDTO updatedEmployee) {
        Optional<Employee> existingEmployee = repository.findById(id);

        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(updatedEmployee.getName());
            employee.setSalary(updatedEmployee.getSalary());
            employee.setDepartment(updatedEmployee.getDepartment());

            return repository.save(employee);
        } else {
            throw new RuntimeException("Employee with ID " + id + " not found");
        }
    }

}