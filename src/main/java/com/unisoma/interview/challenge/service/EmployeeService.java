package com.unisoma.interview.challenge.service;

import java.util.List;
import java.util.Optional;

import com.unisoma.interview.challenge.model.Employee;
import com.unisoma.interview.challenge.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository repository;

  public Employee addOrUpdateOne(Employee Employee) {
    return repository.save(Employee);
  }

  public List<Employee> findAll() {
    return repository.findAll();
  }

  public Optional<Employee> findByTaxId(String taxId) {
    return repository.findByTaxId(taxId);
  }

  public void deleteByTaxId(String taxId) {
    repository.deleteByTaxId(taxId);
  }

  public void deleteAll() {
    repository.deleteAll();
  }
}
