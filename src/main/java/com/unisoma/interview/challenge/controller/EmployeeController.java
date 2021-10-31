package com.unisoma.interview.challenge.controller;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.unisoma.interview.challenge.model.Employee;
import com.unisoma.interview.challenge.model.EmployeeDto;
import com.unisoma.interview.challenge.service.EmployeeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping("/employee")
  public ResponseEntity<List<EmployeeDto>> findAll() {
    try {
      List<Employee> employees = employeeService.findAll();

      if (employees.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      List<EmployeeDto> employeesDto = employees.stream().map(this::convertToDto).collect(Collectors.toList());

      return new ResponseEntity<List<EmployeeDto>>(employeesDto, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/employee/{taxId}")
  public ResponseEntity<EmployeeDto> getById(@PathVariable("taxId") String taxId) {
    Optional<Employee> employeeData = employeeService.findByTaxId(taxId);

    if (employeeData.isPresent()) {
      return new ResponseEntity<EmployeeDto>(convertToDto(employeeData.get()), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/employee")
  public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employeeDto) {
    try {
      Employee employee = employeeService.addOrUpdateOne(
        new Employee(
          employeeDto.getName(), 
          employeeDto.getTaxId(),
          employeeDto.getBirthday(), 
          employeeDto.getPhone(), 
          employeeDto.getAddress(), 
          employeeDto.getSalary()
        )
      );

      return new ResponseEntity<EmployeeDto>(convertToDto(employee), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/employee/{taxId}")
  public ResponseEntity<EmployeeDto> updateOne(@PathVariable("taxId") String taxId, @RequestBody EmployeeDto employeeDto) {
    Optional<Employee> employeeData = employeeService.findByTaxId(taxId);

    if (employeeData.isPresent()) {
      Employee _employee = employeeData.get();
      _employee.setName(employeeDto.getName());
      _employee.setTaxId(employeeDto.getTaxId());
      _employee.setBirthday(employeeDto.getBirthday());
      _employee.setPhone(employeeDto.getPhone());
      _employee.setAddress(employeeDto.getAddress());
      _employee.setSalary(employeeDto.getSalary());

      return new ResponseEntity<>(convertToDto(employeeService.addOrUpdateOne(_employee)), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/employee/{taxId}")
  public ResponseEntity<HttpStatus> deleteOne(@PathVariable("taxId") String taxId) {
    try {
      employeeService.deleteByTaxId(taxId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/employee")
  public ResponseEntity<HttpStatus> deleteAll() {
    try {
      employeeService.deleteAll();
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/employee/{taxId}/raise-salary")
  public ResponseEntity<Map<String, String>> calculateNewSalary(@PathVariable("taxId") String taxId) {
    Optional<Employee> employeeData = employeeService.findByTaxId(taxId);

    if (employeeData.isPresent()) {
      Employee employee = employeeData.get();

      float salaryBeforeRaise = employee.getSalary();
      double percentageBeforeRaise = employee.getSalaryPercentage();

      employee.raiseSalary();

      employeeService.addOrUpdateOne(employee);

      Map<String, String> response = new HashMap<String, String>();

      response.put("tax_id", employee.getTaxId());
      response.put("new_salary", NumberFormat.getCurrencyInstance().format(employee.getSalary()));
      response.put("raise_amount", NumberFormat.getCurrencyInstance().format(employee.getSalary() - salaryBeforeRaise));
      response.put("salary_increase_percentage", NumberFormat.getPercentInstance().format(percentageBeforeRaise));

      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/employee/{taxId}/tax-amount")
  public ResponseEntity<Map<String, String>> calculateTaxAmount(@PathVariable("taxId") String taxId) {
    Optional<Employee> employeeData = employeeService.findByTaxId(taxId);

    if (employeeData.isPresent()) {
      Employee employee = employeeData.get();

      Map<String, String> response = new HashMap<String, String>();

      response.put("tax_id", employee.getTaxId());
      response.put("tax_amount",
          employee.getTaxAmount() == 0 ? "Isento" : NumberFormat.getCurrencyInstance().format(employee.getTaxAmount()));
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  private EmployeeDto convertToDto(Employee employee) {
    return modelMapper.map(employee, EmployeeDto.class);
  }
}
