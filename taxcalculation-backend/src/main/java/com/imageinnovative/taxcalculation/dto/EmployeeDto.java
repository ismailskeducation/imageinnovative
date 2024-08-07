package com.imageinnovative.taxcalculation.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @NotBlank(message = "Employee ID is mandatory")
    private long employeeId;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Date of Joining (DOJ) is mandatory")
    private String doj;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary should be greater than 0")
    private double salary;

    @NotEmpty(message = "Phone Numbers are mandatory")
    private List<@Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number") String> phoneNumbers;

    private double yearlySalary;
    private double tax;
    private double cess;


}
