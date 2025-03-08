package com.spring.employeePayroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayrollDTO {
    private String name;
    private double salary;
    private String department;
}