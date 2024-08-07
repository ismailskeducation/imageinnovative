package com.imageinnovative.taxcalculation.repository;


import com.imageinnovative.taxcalculation.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // Custom query methods can be added here if needed
}
