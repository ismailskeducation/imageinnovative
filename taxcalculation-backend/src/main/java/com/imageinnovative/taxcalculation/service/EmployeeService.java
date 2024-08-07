package com.imageinnovative.taxcalculation.service;

import com.imageinnovative.taxcalculation.dto.EmployeeDto;
import com.imageinnovative.taxcalculation.model.Employee;
import com.imageinnovative.taxcalculation.model.PhoneNumber;
import com.imageinnovative.taxcalculation.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.imageinnovative.taxcalculation.dto.EmployeeDto;
import com.imageinnovative.taxcalculation.model.Employee;
import com.imageinnovative.taxcalculation.model.PhoneNumber;
import com.imageinnovative.taxcalculation.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDoj(LocalDate.parse(employeeDto.getDoj()));
        employee.setSalary(employeeDto.getSalary());

        List<PhoneNumber> phoneNumbers = employeeDto.getPhoneNumbers().stream().map(phone -> {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setPhoneNumber(phone);
                    phoneNumber.setEmployee(employee);
                    return phoneNumber;
                }).collect(Collectors.toList());

        employee.setPhoneNumbers(phoneNumbers);

        employeeRepository.save(employee);
    }

    public EmployeeDto calculateTax(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

        double monthlySalary = employee.getSalary();
        double yearlySalary = monthlySalary * 12;

        LocalDate doj = employee.getDoj();
        LocalDate startOfCurrentFY = LocalDate.of(LocalDate.now().getYear(), 4, 1);
        if (doj.isAfter(startOfCurrentFY)) {
            long daysWorked = LocalDate.now().toEpochDay() - doj.toEpochDay();
            yearlySalary = (monthlySalary / 30) * daysWorked;
        }

        double tax = 0.0;
        if (yearlySalary > 1000000) {
            tax += (yearlySalary - 1000000) * 0.20;
            yearlySalary = 1000000;
        }
        if (yearlySalary > 500000) {
            tax += (yearlySalary - 500000) * 0.10;
            yearlySalary = 500000;
        }
        if (yearlySalary > 250000) {
            tax += (yearlySalary - 250000) * 0.05;
        }

        double cess = yearlySalary > 2500000 ? (yearlySalary - 2500000) * 0.02 : 0.0;

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setYearlySalary(yearlySalary);
        employeeDto.setTax(tax);
        employeeDto.setCess(cess);

        return employeeDto;
    }
}
