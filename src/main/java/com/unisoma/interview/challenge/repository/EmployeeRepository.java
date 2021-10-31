package com.unisoma.interview.challenge.repository;

import java.util.Optional;

import com.unisoma.interview.challenge.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
  Optional<Employee> findByTaxId(String taxId);

  Optional<Employee> deleteByTaxId(String taxId);
}
